# 推送到 GitHub 并发布 Release
# 用法: .\upload_to_github.ps1 -owner "你的GitHub用户名" -token "你的Personal Access Token"

param(
    [Parameter(Mandatory=$true)]
    [string]$owner,

    [Parameter(Mandatory=$true)]
    [string]$token
)

$repo = "365days-daysmat"
$tag = "v1.0.0"
$apkPath = "app/build/outputs/apk/debug/app-debug.apk"

# 设置 remote 并推送
$remoteUrl = "https://$owner`:$token@github.com/$owner/$repo.git"
git remote remove origin 2>$null
git remote add origin $remoteUrl

# 确保分支名为 main
git branch -M main

Write-Host "正在推送代码到 GitHub..."
git push -u origin main --force
if ($LASTEXITCODE -ne 0) {
    Write-Error "代码推送失败，请检查用户名、Token 和仓库名。"
    exit 1
}

# 创建并推送 tag
Write-Host "正在创建 tag $tag ..."
git tag -a $tag -m "Release $tag" -f
git push origin $tag --force
if ($LASTEXITCODE -ne 0) {
    Write-Error "Tag 推送失败。"
    exit 1
}

# 创建 GitHub Release
$releaseBody = @"
365days 首个版本发布。

## 主要功能
- 年/月/周/日多种日历视图
- 日程管理与纪念日倒数
- iOS 26/27 风格液态玻璃 UI
- 自定义背景（相册/内置模板）、透明度与模糊度调节
- 自定义强调色、默认提醒时间、一周起始日设置

## 安装
下载附件中的 `app-debug.apk` 安装即可。

## 许可
BSD-3-Clause
"@

$releaseJson = @{
    tag_name = $tag
    name = $tag
    body = $releaseBody
    draft = $false
    prerelease = $false
} | ConvertTo-Json

Write-Host "正在创建 GitHub Release..."
$releaseHeaders = @{
    Authorization = "token $token"
    Accept = "application/vnd.github.v3+json"
}
$releaseResponse = Invoke-RestMethod -Uri "https://api.github.com/repos/$owner/$repo/releases" -Method Post -Headers $releaseHeaders -Body $releaseJson -ContentType "application/json"
$uploadUrl = $releaseResponse.upload_url -replace "\\{\\?name,label\\}", ""

# 上传 APK
if (Test-Path $apkPath) {
    Write-Host "正在上传 APK..."
    $apkName = [System.IO.Path]::GetFileName($apkPath)
    $uploadUri = "$uploadUrl?name=$apkName"
    $apkBytes = [System.IO.File]::ReadAllBytes($apkPath)
    $uploadHeaders = @{
        Authorization = "token $token"
        Accept = "application/vnd.github.v3+json"
        "Content-Type" = "application/vnd.android.package-archive"
    }
    Invoke-RestMethod -Uri $uploadUri -Method Post -Headers $uploadHeaders -Body $apkBytes -ContentType "application/vnd.android.package-archive" | Out-Null
    Write-Host "APK 上传成功！"
} else {
    Write-Warning "未找到 APK 文件：$apkPath"
}

Write-Host "完成！Release 地址：$($releaseResponse.html_url)"

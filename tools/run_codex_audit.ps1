$Host.UI.RawUI.WindowTitle = 'Codex - Auditoria Forense Web (Cancionero Digital)'
$root = Resolve-Path (Join-Path $PSScriptRoot "..")
Set-Location $root

Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "   CODEX: AUDITORIA FORENSE LINEA A LINEA (WEB)       " -ForegroundColor Yellow
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "Iniciando sesion de Codex para auditoria forense web..." -ForegroundColor Green
Write-Host "Directorio: $root" -ForegroundColor DarkGray
Write-Host ""

$prompt = Get-Content (Join-Path $PSScriptRoot "codex_forensic_prompt.md") -Raw
codex $prompt

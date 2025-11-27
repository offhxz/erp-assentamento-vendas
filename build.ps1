param(
    [string]$JdkPath = $env:JAVA_HOME
)

if (-not $JdkPath) {
    Write-Error "No JDK path provided and `JAVA_HOME` is not set. Provide a JDK 21 path as the first argument or set the `JAVA_HOME` environment variable."
    exit 1
}

$bin = Join-Path $JdkPath 'bin'
if (-not (Test-Path $bin)) {
    Write-Error "JDK bin not found at $bin"
    exit 1
}

# Set environment for this session
$env:JAVA_HOME = $JdkPath
$env:PATH = "$bin;$env:PATH"

$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Definition
$src = Join-Path $scriptRoot 'src\main\java'
$out = Join-Path $scriptRoot 'out'

if (-not (Test-Path $src)) {
    Write-Error "Source folder not found: $src"
    exit 1
}

if (-not (Test-Path $out)) {
    New-Item -ItemType Directory -Path $out | Out-Null
}

$files = Get-ChildItem -Path $src -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
if (-not $files) {
    Write-Error "No Java source files found under $src"
    exit 1
}

Write-Host "Compiling with JDK at: $JdkPath (java version)"
& "$bin\java" -version

Write-Host "Running javac --release 21 ..."
& "$bin\javac" --release 21 -d $out -cp "lib/*" $files

if ($LASTEXITCODE -ne 0) {
    Write-Error "Compilation failed with exit code $LASTEXITCODE"
    exit $LASTEXITCODE
}

Write-Host "Compilation succeeded. To run the app:`n  java -cp \"$out;lib/*\" br.edu.ifsp.hto.cooperativa.vendas.App"
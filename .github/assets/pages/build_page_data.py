import json
import re
import shutil
from pathlib import Path

input_root = Path("_input")
site_root = Path("_site")
repo_root = Path(".")

description_pattern = re.compile(r'^\s*description\s*=\s*"([^"]+)"', re.MULTILINE)


def module_description(module: str) -> str:
    gradle_file = repo_root / module / "build.gradle.kts"
    if not gradle_file.is_file():
        return f"Documentation for {module}."
    content = gradle_file.read_text(encoding="utf-8")
    match = description_pattern.search(content)
    if match:
        return match.group(1)
    return f"Documentation for {module}."


def sync_dir_contents(source: Path, destination: Path) -> None:
    destination.mkdir(parents=True, exist_ok=True)
    for child in source.iterdir():
        target = destination / child.name
        if child.is_dir():
            shutil.copytree(child, target, dirs_exist_ok=True)
        else:
            shutil.copy2(child, target)


test_reports = []
for report_dir in sorted(input_root.glob("**/build/reports/tests/test")):
    if not report_dir.is_dir():
        continue
    module = str(report_dir.relative_to(input_root)).replace("\\", "/")
    module = module[: -len("/build/reports/tests/test")]
    sync_dir_contents(report_dir, site_root / "test-report" / module)
    test_reports.append(
        {
            "module": module,
            "description": module_description(module),
        }
    )

javadocs = []
for doc_dir in sorted(input_root.glob("**/build/docs/javadoc")):
    if not doc_dir.is_dir():
        continue
    module = str(doc_dir.relative_to(input_root)).replace("\\", "/")
    module = module[: -len("/build/docs/javadoc")]
    sync_dir_contents(doc_dir, site_root / "javadocs" / module)
    javadocs.append(
        {
            "module": module,
            "description": module_description(module),
        }
    )

payload = {
    "testReports": test_reports,
    "javadocs": javadocs,
}
(site_root / "page-data.json").write_text(json.dumps(payload), encoding="utf-8")


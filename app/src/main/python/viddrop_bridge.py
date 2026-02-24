from yt_dlp import YoutubeDL
from yt_dlp.version import __version__


def run_command(cmd: str) -> str:
    name = (cmd or "").strip().lower()

    if name == "version":
        return f"yt-dlp {__version__}"

    if name == "extractors":
        with YoutubeDL({}) as ydl:
            extractor_names = ydl.list_extractors(age_limit=None)
        return "\n".join(extractor_names)

    if name == "update":
        return "Update is handled by shipping a new app build with newer pip yt-dlp."

    return "Unsupported command. Use: version | extractors | update"


def download_video(url: str, out_dir: str, fmt: str = "bv*+ba/b") -> str:
    clean_url = (url or "").strip()
    if not clean_url:
        return "ERROR: empty URL"

    logs = []

    def hook(status):
        state = status.get("status")
        if state == "downloading":
            pct = status.get("_percent_str", "?")
            speed = status.get("_speed_str", "?")
            logs.append(f"DOWNLOADING {pct} @ {speed}")
        elif state == "finished":
            logs.append("Download finished. Merging...")

    opts = {
        "format": fmt,
        "merge_output_format": "mp4",
        "outtmpl": f"{out_dir}/%(title).120s.%(ext)s",
        "noplaylist": True,
        "progress_hooks": [hook],
        "quiet": True,
        "no_warnings": True,
    }

    try:
        with YoutubeDL(opts) as ydl:
            info = ydl.extract_info(clean_url, download=True)
        title = info.get("title", "unknown") if isinstance(info, dict) else "unknown"
        logs.append(f"SUCCESS: {title}")
        return "\n".join(logs)
    except Exception as exc:
        logs.append(f"ERROR: {exc}")
        return "\n".join(logs)

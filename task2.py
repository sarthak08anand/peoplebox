def get_status_message(status: str) -> str:
    return {'active': 'Active', 'inactive': 'Inactive'}.get(status, 'Unknown')

def process(data: dict) -> None:
    print(get_status_message(data.get('status', '')))

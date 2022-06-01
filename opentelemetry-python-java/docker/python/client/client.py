from requests import get
from sys import argv

assert len(argv) == 2

requested = get(
    "http://java-end:8081/greeting",
    params={"param": argv[1]},
)

assert requested.status_code == 200
print("-------------- Python client request java server got：" + requested.text)

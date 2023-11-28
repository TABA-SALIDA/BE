import os
import pyodbc
import subprocess

# setting directory to watch
directory_to_watch = "/home/ec2-user/data"

# connection to database


# using inotify
process = subprocess.Popen(['inotifywait', '-m', '-e', 'create', '-e', 'modify','-e', 'close_write', '--format', '%w%f', directory_to_watch], stdout=subprocess.PIPE)

while True:
    line = process.stdout.readline()
    if not line:
        break
    print(line)


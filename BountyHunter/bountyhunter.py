import requests, json, sys, os
import urllib
import shutil
import bz2
from bz2 import decompress

if len(sys.argv) < 2:
    print("Usage: python3 bountyhunter.py <hero_name>")
    exit(1)

r = requests.get('https://api.opendota.com/api/heroes')
heroes = json.loads(r.text)
heroID = -1

for h in heroes:
    if h["localized_name"].lower() == sys.argv[1].lower():
        heroID = h["id"]
        break

if heroID == -1:
    print("Your hero doesn't exist")
    exit(1)

r = requests.get('https://api.opendota.com/api/heroes/'+str(heroID)+'/matches')
matches = json.loads(r.text)

for m in matches:
    r = requests.get('https://api.opendota.com/api/matches/'+str(m["match_id"]))
    candidateMatch = json.loads(r.text)
    for x in candidateMatch["players"]:
        if x["hero_id"] == heroID:
            if candidateMatch["replay_url"]:
                print("Found a replay @"+ candidateMatch["replay_url"] +" , downloading...")
                filepath = 'replay_'+str(candidateMatch["match_id"])+'.dem.bz2'
                with requests.get(candidateMatch["replay_url"], stream=True) as response, open(filepath, 'wb') as out_file:
                    print("Copying...")
                    for chunk in response.iter_content(chunk_size=1024):
                        if chunk: # filter out keep-alive new chunks
                            out_file.write(chunk)
                    out_file.close()
                    print("Decompressing...")
                    os.system("bzip2 -d "+filepath)

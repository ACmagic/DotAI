function sleep(n)
  os.execute("sleep " .. tonumber(n))
end

https = require('ssl.https')
http = require('socket.http')
json = require('json')

if #arg < 1 then
    print("Usage: lua bountyhunter.lua <hero_name>")
    return
end

https.TIMEOUT= 10
local link = 'https://api.opendota.com/api/heroes'
local resp = {}
local body, code, headers = https.request{
                                url = link,
                                headers = { ['Connection'] = 'close' },
                                sink = ltn12.sink.table(resp)
                                 }

local heroes = json.decode(table.concat(resp))
local heroID = -1

for i, h in ipairs(heroes) do
    if h.localized_name:lower() == arg[1]:lower() then
        heroID = i - 1
        break
    end
end

if heroID == -1 then
    print("Your hero doesn't exist")
    return
end
sleep(1)
link = 'https://api.opendota.com/api/heroes/'.. heroID ..'/matches'
local resp = {}
local body, code, headers = https.request{
                                url = link,
                                headers = { ['Connection'] = 'close' },
                                sink = ltn12.sink.table(resp)
                            }
local matches = json.decode(table.concat(resp))
sleep(1)
for i, m in ipairs(matches) do

    link = 'https://api.opendota.com/api/matches/' .. m.match_id
    local resp = {}
    local body, code, headers = https.request{
                                    url = link,
                                    headers = { ['Connection'] = 'close' },
                                    sink = ltn12.sink.table(resp)
                                }
    local candidateMatch = json.decode(table.concat(resp))
    for j, x in ipairs(candidateMatch.players) do
        if x.hero_id == heroID then
            if candidateMatch.replay_url ~= nil then
                print("Found a replay, downloading...")
                local file = http.request(candidateMatch.replay_url)
                local f = assert(io.open(candidateMatch.match_id .. ".dem.bz2", 'wb'))
                f:write(file)
                f:close()
                print("Decompressing...")
                os.execute("bzip2 -d " ..candidateMatch.match_id.. ".dem.bz2")
                os.execute("java -jar datai.jar "..candidateMatch.match_id.. ".dem &")
            end
            break
        else
            if(j == 10) then
                print("No " .. arg[1] .. " in this match, skipping")
            end
        end
    end
end
print("END")

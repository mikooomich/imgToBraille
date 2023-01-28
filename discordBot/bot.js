// Welcome to my jank code
const version = 'v0.0.1 (00001)';

const config = require("./config.json");


const fs = require('fs');
const {Client, GatewayIntentBits, Partials} = require('discord.js');
const client = new Client({ intents: [GatewayIntentBits.GuildVoiceStates, GatewayIntentBits.Guilds,
  GatewayIntentBits.GuildMessages, GatewayIntentBits.MessageContent], partials: [Partials.Channel] });


const {exec} = require('child_process');
const BINARY = '"' + config.binary + '"';
const PATH = config.path;
const PREFIX = config.token;



let downloader;



client.login(config.token);





let frames = []; // ARRAY of frames (strings)

/**
 * Appened to frames when new data arrives
 */
async function watchDog() {
  downloader.stdout.on('data', function(data) {
    frames.push(data);
  });
}



if (!fs.existsSync(PATH)) {
  fs.mkdirSync(PATH);
}

if (!fs.existsSync(PATH + "/frames")) {
  fs.mkdirSync(PATH + "/frames");
}






/**
 * main bot code
 */
client.on('messageCreate', async recMsg => {
  if (recMsg.author == client.user ) {
    return;
  }
  else {




    switch (recMsg.content.toLowerCase()) {
      case PREFIX + "play":

        if (frames == undefined) {
          recMsg.channel.send("No data in framebuffer, run -compile first");
          return;
        }

        await recMsg.channel.send("hewwo").then((msg)=> {
            function spamFrames(i) {
              if (i > frames.length) {
                return;
              }
              msg.edit(frames[i] + "\n" + i/frames.length*100 + "%");
              setTimeout(() => {
                spamFrames(i+1) // print next frame
              }, 1000/1)
            }

            spamFrames(0);
        })
        break;

      case PREFIX + "del":
        fs.rmSync(PATH + "/frames", { recursive: true, force: true });
        if (!fs.existsSync(PATH + "/frames")) {
          fs.mkdirSync(PATH + "/frames");
        }
        
        recMsg.channel.send("deleted");
        break

      case PREFIX + "exit":
        recMsg.channel.send("bye");
        console.log("bye");
        process.exit();
        break;

      // convert frames to text
      case PREFIX + "compile":  
        if (fs.opendirSync(PATH + "/frames").read == undefined ) {
          recMsg.channel.send("no frames to process, run -export first");
          return;
        }

        recMsg.channel.send("compiling, do not interupt");
        console.log("compiling, do not interupt");
        frames = [];
        downloaderBusy = true;
        downloader = exec("java -jar " + dlBinary, {maxBuffer: 1024 * 1000000},  (err, stdout, stderr) => {
          if (err) {
            console.error(err);



            downloaderBusy = false;
            downloader = undefined;
            return;
          }
          recMsg.channel.send("Finished");
          downloaderBusy = false;
          downloader = undefined;
        });

        watchDog();
        break;


        // convert video to frames
        case PREFIX + "export":
          downloader = exec(`ffmpeg.exe -i "vid.mp4" -vf fps=5 ./frames/out%d.png`, {cwd: "./video"}, (err, stdout, stderr) => {
            if (err) {
              console.error(err);
              return;
            }
            recMsg.channel.send("Finished export, run compile and then play");
            downloaderBusy = false;
            downloader = undefined;
          });

          downloader.stdout.on('data', function(data) {
            console.log(data)
          }) 
            
          break;
      }
  }
})

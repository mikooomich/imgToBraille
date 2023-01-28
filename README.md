# Hai!
The NodeJS part utilizes the Java part to process frames into Braille text, then Frankenstein-ed onto a Discord bot (serving as the interface).
The Java part converts series of image(es) in a directory to an image in text form (Unicode Braille).
  

### Usage
Run:
-export to convert video file to images (currently ffmpeg is hard coded)
-compile to convert images to Unicode text "frames"
-play to commence playback


### How it works

Each frame of a video is exported into images (use ffmpeg or something). Then the image is downscaled (integer) to fit in the maximum allowed resolution. In Discord this is 2000 characters, or 16000 characters as braille has 4 pixels per character. The downscaled image is converted into braille with the following mapping:

0 3 <br>
1 4 <br>
2 5 <br>
6 7 <br>


This works the same as binary numbers, where this is an 8 bit number. The image is thrown into Discord bot's frame buffer via stud out, where playback is limited to 1fps due to Discord rate-limits. Anything more causes the API to crap itself and become a stuttery mess.

### Java args
\<Threshold> \<Max size> \<Path> (IN THIS ORDER)

Threshold: (should be 0-255) For tuning the brightness threshold of the image, may help for images too dark, or blown out. Below this number, this brightness value will be considered a black pixel, equal to and above, this will be considered a white pixel.

Max size: The maximum pixel count of the image. **Note**: One braille character is equal to 8 pixels. For example, to fit within Discord's character limit, the max threshold can be set to 16000 (2000 x 4).

Path: File directory containing frames (images). The slash at end of the director is not optional. Ex "./folder/frame folder/"


### Future enhancements
- Multi-thread image compile
- Terminal support 
- Less jank NodeJS code

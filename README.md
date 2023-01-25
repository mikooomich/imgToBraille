# Hai!
The NodeJS part utilizes the Java part to process frames into Braille text, then Frankenstein-ed onto a Discord bot (serving as the interface).
The Java part converts series of image(es) in a directory to an image in text form (Unicode Braille).
  

### Java args
 \<Threshold> \<Max size> \<Path> (IN THIS ORDER)

Threshold: (should be 0-255) For tuning the brightness threshold of the image, may help for images too dark, or blown out. Below this number, this brightness value will be considered a black pixel, equal to and above, this will be considered a white pixel.

Max size: The maximum pixel count of the image. **Note**: One braille character is equal to 8 pixels. For example, to fit within Discord's character limit, the max threshold can be set to 16000 (2000 x 4).

Path: File directory containing frames (images). The slash at end of the director is not optional. Ex "./folder/frame folder/"



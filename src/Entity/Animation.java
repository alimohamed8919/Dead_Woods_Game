package Entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Animation {
	
   BufferedImage[] image;

   int current = 0;

   int delay;

   int duration;

   public Animation(String file, int count, int duration)
   {
      image = new BufferedImage[count];

      for(int i = 0; i < count; i++)
      {
         try {
			image[i] = ImageIO.read(getClass().getResourceAsStream("/Characters/" + file + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
      }

      this.duration = duration;
      delay = duration;
   }

   public Image stillImage()
   {
      return image[0];
   }

   public Image nextImage()
   {
       if(delay == 0)
       {
          current++;

          if(current == image.length)
          {
             current = 1;
          }

          delay = duration;
       }
       else
       {
          delay--;
       }

       return image[current];
   }
}


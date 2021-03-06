package com.example.nharshith.friendlyghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictonary {

    private ArrayList<String>words;
    private Random random;

    public SimpleDictonary(InputStream inputStream) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
        words = new ArrayList<>();
        String line ="";
        while((line = bfr.readLine())!=null){
          String word = line.trim();
          if(word.length() >= 4) {
              words.add(word);
          }
        }
    }



    public boolean isGoodWord(String word){
        return words.contains(word);
    }
    public String getGoodWord(String prefix) {
        if (prefix == null || prefix.equals("")){
            return words.get(random.nextInt(words.size()));
        }
         else{
           int low =0;
           int high = words.size();
           int mid;
           while(low < high){
               mid = low+(high-low)/2;
               if(words.get(mid).startsWith(prefix)){
                       return words.get(mid);
               }else if(prefix.compareTo(words.get(mid)) > 0){
                  low = mid + 1;
               }else{
                   high = mid - 1;
               }
           }
           return null;
        }
    }
}

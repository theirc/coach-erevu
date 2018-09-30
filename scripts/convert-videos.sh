find . -type f -iname '*.mov' -exec sh -c '
  for file do
    outputfile=${file}-hevc.mp4
    donefile=${outputfile}.done

    if [ ! -f "$donefile" ]; then
      echo -e "Compressing $file  >>  ${outputfile}";
      ffmpeg -i "$file" -c:v libx265 -c:a aac -b:a 128k "$outputfile";
      touch "$donefile";
  fi

  done
' sh {} +

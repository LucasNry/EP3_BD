# File to compile all typescript files in order for them to be used as js by the html file
cd ~/Documents/EP3_BD/client/src
paths="${list_paths}"
echo $paths
# for path in $paths;
# do
#     echo $path
# done

list_paths() {
    ls -p
}
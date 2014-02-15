# Remove the flow from the use cases wiki page
#   Reads in all text from inCase.txt
#   Removes all lines beginning with '>'
#   Writes result to outCase.txt
def mainfunc():
    
    inFile = open("inCase.txt", "r")
    
    outFile = open("outCase.txt", "w")

    buf = ""
    line = inFile.readline()    
    while line != "":
        if line[0] != '>':
            buf += line
        line = inFile.readline()
        
    outFile.write(buf)

if __name__ == "__main__":
    mainfunc()
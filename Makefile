.phony: all run clean

NAME = "Main"
SRC = "src"

all:
	@echo "Compiling..."
	javac src/*.java

run: all
	@echo "Running..."
	java -classpath $(SRC) $(NAME)

clean:
	rm *.class

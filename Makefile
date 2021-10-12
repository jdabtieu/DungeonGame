prod:
	@./build.sh

dev:
	@mkdir -p bin
	javac -sourcepath src -d bin src/*.java
 
clean:
	@rm -rf bin/*
	@rm DungeonGame.jar

update:
	@git pull

rprod:
	java -jar DungeonGame.jar

rdev:
	java -cp bin Main


dev:
	@mkdir -p bin
	javac -sourcepath src -d bin src/*.java

prod:
	@./build.sh

clean:
	@rm DungeonGame.jar

update:
	git pull

run-prod:
	java -jar DungeonGame.jar

run:
	java -cp bin Main


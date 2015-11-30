classpath = "${CLASSPATH}:build"

all: clean
	mkdir build
	mkdir antlrBuild
	java org.antlr.v4.Tool MASB.g4 -o antlrBuild
	javac -d build -cp $(classpath) src/SymbolTable/*.java
	javac -d build -cp $(classpath) src/CodeGen/*.java
	javac -d build -cp $(classpath) src/ASTree/*.java
	javac -d build -cp $(classpath) src/ASTree/exprs/*.java src/ASTree/stmts/*.java
	javac -d build -cp $(classpath) antlrBuild/MASB*.java
	javac -d build -cp $(classpath) src/*.java
	java -cp $(classpath) Main tests/test1.masb

clean:
	rm -rf build
	rm -rf antlrBuild

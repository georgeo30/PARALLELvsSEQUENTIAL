JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc


default:
	$(JAVAC) -d $(BINDIR) $(SRCDIR)/*.java $<

clean:
	rm $(BINDIR)/*.class
	rm -Rf doc

doc:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java







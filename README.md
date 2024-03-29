# What is this?
It does Pig Latin.

When you run it, it should pop open a swing frame with two text areas. The
first one is editable and you can enter any text in there. The second will
have the Pig Latin version.

## Anything cool?
It comes with a public domain copy of *The Adventures of Sherlock Holmes*. So
you could read it in Pig Latin. If you want.

It also uses a `WeakHashMap` to cache paragraphs and words so it doesn't have
to recompute them for each update. It probably doesn't make execution much
faster than just doing the actual processing all over again. I recall that
using keys will require iteration over the string via a call to
`equals(Object)` which is comparable to the iteration over the string required
for computation. Heck, it might make it slower. But, I had good intentions.

The UI is dynamic. It updates as you type. This has a downside, though, because
when there's a large body of text, the re-computation for the second text area
can cause some unresponsiveness to the UI. In a more serious scenario, I'd do it
via another thread or have partial updates of the second text area.

It does some smart-ish case matching. So, if you enter `Foobar` you'll get
`Oobarfay` to preserve the first letter upper case. If you enter `FOOBAR`,
you'll get `OOBARFAY` to preserve all upper case. If you enter `fooBar` you'll
get `ooBarfay` to get lower case or other miscellaneous case preservation.

I also went through some pains to make it preserve extra characters. So,
newlines, multiple spaces, and other punctuations are preserved. This is
because all non-word characters are used as a delimiter and splitting
is done with preservation of delimiters. This means, however, that certain
words like `they're` or `I'm` will be translated to `heytay'eray` and
`Iway'may`. I don't know if this is correct but I'm going to assume it is.

# How do I build?
You could use Maven directly to build and get dependencies. Frankly, I don't
know much about manually invoking Maven but it should work in that regard.
Supposedly, you could just do:

	mvn clean install

Or you could just import it as a Netbeans project by going to:

	Files -> Import Project -> From Zip
	
And then selecting the original zip file. Netbeans should be able to figure out
that it's a Maven project and building the project should pull in all the
dependencies. Note that this program does not depend on anything for execution
but required JUnit for testing.

Also, Netbeans doesn't support UAC paths for shell execution which is required
when executing Maven when the project is on a network drive. Thus, it will not
work on Frostburg machine without first moving the project outside the default
projects directory.

# Where is documentation?
The code has has javadoc so you can build it and view it.

Then there's the readme. If you're reading this, you've found it!
This is also written as a markdown file. So you could process it as HTML and
make it all pretty.

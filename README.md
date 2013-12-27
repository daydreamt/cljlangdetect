# cljlangdetect

A simple Clojure wrapper of the java language-detection library.
Most features of the original are not implemented yet.

Provides language detection of text, using naive bayes.

For the list of supported languages, as well as other features, see http://code.google.com/p/language-detection/ .

## Usage
    (detect "This is still a very early version, this is the only implemented function.")
    =>"en"


## License
Distributed under the Apache License, Version 2.0, the same as language-detection.

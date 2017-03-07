package javase.unit3.task3;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Searcher for sentences in text and links on pictures in them.
 */
public class RegExpSearcher {
    public static final Pattern LINK_PATTERN = Pattern.compile("[Рр]ис(\\.\\s?|ун(ка|ке|ок)\\s)(\\d+)");
    public static final int LINK_GROUP_IN_PATTERN = 3;
    //regexp for sentences was inspired by muh3gpab's pattern in the slack chat.
    public static final Pattern SENTENCE_PATTERN = Pattern.compile("[А-Я0-9].*?([.]{3}|(?<![Рр]ис)[.]|[?!])");

    private BufferedReader reader;
    private boolean linksSequential;
    private boolean inputHasLinks;
    private List<String> sentencesWithLinks;

    public static void main(String[] args) {
        try {
            RegExpSearcher searcher = RegExpSearcher.from(new File("article.html"), Charset.forName("windows-1251"));

            StringBuilder stringBuilder = new StringBuilder("Links in text is ");

            if (searcher.isLinksSequential()) {
                stringBuilder.append("sequential. ");
            } else {
                stringBuilder.append("non sequential. ");
            }

            stringBuilder.append("List of all sentences with links (")
                    .append(searcher.getSentencesWithLinks().size())
                    .append("):");

            System.out.println(stringBuilder);

            searcher.getSentencesWithLinks()
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private RegExpSearcher() {}

    /**
     * Creates a searcher from reader.
     *
     * @param reader an object to search from
     * @return searcher with results
     * @throws IOException if reader contains incorrect data
     */
    public static RegExpSearcher from(Reader reader) throws IOException {
        Objects.requireNonNull(reader);

        RegExpSearcher searcher = new RegExpSearcher();
        searcher.reader = new BufferedReader(reader);
        searcher.sentencesWithLinks = new ArrayList<>();
        try {
            searcher.analyseReader();
        } finally {
            searcher.reader.close();
        }

        return searcher;
    }

    /**
     * Creates a searcher from string.
     *
     * @param stringToSearch a string to search from
     * @return searcher with results
     * @throws IOException if reader contains incorrect data
     */
    public static RegExpSearcher from(String stringToSearch) throws IOException {
        Objects.requireNonNull(stringToSearch);

        return from(new StringReader(stringToSearch));
    }

    /**
     * Creates a searcher from file and charset.
     *
     * @param file a file to search from
     * @param charset charset for file encoding
     * @return searcher with results
     * @throws IOException if reader contains incorrect data
     */
    public static RegExpSearcher from(File file, Charset charset) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(charset);

        return from(new InputStreamReader(new FileInputStream(file), charset));
    }

    /**
     * Creates a searcher from file with default charset.
     *
     * @param file a file to search from
     * @return searcher with results
     * @throws IOException if reader contains incorrect data
     */
    public static RegExpSearcher from(File file) throws IOException {
        Objects.requireNonNull(file);

        return from(new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()));
    }

    /**
     * Returns all sentences which contains links.
     *
     * @return
     */
    public List<String> getSentencesWithLinks() {
        return sentencesWithLinks;
    }

    /**
     * Returns if links in given data is sequential or not.
     *
     * @return
     */
    public boolean isLinksSequential() {
        return linksSequential;
    }

    /**
     * Returns if given data contains any links or not.
     *
     * @return
     */
    public boolean hasLinks() {
        return inputHasLinks;
    }

    private void analyseReader() throws IOException {
        linksSequential = true;
        int highestLinkNumber = 0;
        String line;

        while ((line = reader.readLine()) != null) {
            Matcher sentenceMather = SENTENCE_PATTERN.matcher(line);

            while (sentenceMather.find()) {
                String sentence = sentenceMather.group();

                Matcher linkMather = LINK_PATTERN.matcher(sentence);

                boolean sentenceHasAnyLinks = false;

                while (linkMather.find()) {
                    int linkNumber = Integer.valueOf(linkMather.group(LINK_GROUP_IN_PATTERN));

                    if (linkNumber > highestLinkNumber) {
                        highestLinkNumber = linkNumber;
                    } else if (linkNumber < highestLinkNumber) {
                        linksSequential = false;
                    }

                    sentenceHasAnyLinks = true;
                }

                if (sentenceHasAnyLinks) {
                    sentencesWithLinks.add(sentence);
                }
            }
        }

        if (highestLinkNumber > 0) {
            inputHasLinks = true;
        }
    }
}

package javase.unit3.task3;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrey on 26.02.2017.
 */
public class RegExpSearcher {
    public static final Pattern LINK_PATTERN = Pattern.compile("[Рр]ис(\\.\\s?|ун(ка|ке|ок)\\s)(\\d+)");
    public static final int LINK_GROUP_IN_PATTERN = 3;
    public static final Pattern SENTENCE_PATTERN = Pattern.compile("([\\.!?]|^)((([Рр]ис\\.\\s?(\\d+))|[^\\.!?])+[\\.!?])");
    public static final int SENTENCE_GROUP_IN_PATTERN = 2;

    private BufferedReader reader;
    private boolean linksSequential;
    private boolean inputHasLinks;
    private List<String> sentencesWithLinks;

    public static void main(String[] args) {
        try {
            RegExpSearcher searcher = RegExpSearcher.from(new File("articl2e.html"), Charset.forName("windows-1251"));

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

    public static RegExpSearcher from(String stringToSearch) throws IOException {
        Objects.requireNonNull(stringToSearch);

        return from(new StringReader(stringToSearch));
    }

    public static RegExpSearcher from(File file, Charset charset) throws IOException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(charset);

        return from(new InputStreamReader(new FileInputStream(file), charset));
    }

    public static RegExpSearcher from(File file) throws IOException {
        Objects.requireNonNull(file);

        return from(new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()));
    }

    public List<String> getSentencesWithLinks() {
        return sentencesWithLinks;
    }

    public boolean isLinksSequential() {
        return linksSequential;
    }

    public boolean hasLinks() {
        return inputHasLinks;
    }

    private void analyseReader() throws IOException {
        linksSequential = true;
        int highestLinkNumber = 0;
        String line;

        while ((line = reader.readLine()) != null) {
            Matcher linkMather = LINK_PATTERN.matcher(line);

            int lastSentenceIndex = 0;

            while (linkMather.find()) {
                int linkNumber = Integer.valueOf(linkMather.group(LINK_GROUP_IN_PATTERN));

                if (linkNumber > highestLinkNumber) {
                    highestLinkNumber = linkNumber;
                } else if (linkNumber < highestLinkNumber) {
                    linksSequential = false;
                }

                Matcher sentenceMather = SENTENCE_PATTERN.matcher(line);

                if (sentenceMather.find(lastSentenceIndex)) {
                    if (LINK_PATTERN.matcher(sentenceMather.group()).find()) {
                        sentencesWithLinks.add(sentenceMather.group(SENTENCE_GROUP_IN_PATTERN));
                    }
                    lastSentenceIndex = sentenceMather.end() - 1;
                }
            }
        }

        if (highestLinkNumber > 0) {
            inputHasLinks = true;
        }
    }
}

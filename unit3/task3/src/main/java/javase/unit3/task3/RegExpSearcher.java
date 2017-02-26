package javase.unit3.task3;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by andrey on 26.02.2017.
 */
public class RegExpSearcher {
    public static final Pattern LINK_PATTERN = Pattern.compile("[Рр]ис((\\.\\s?)|(ун((ке)|(ка)|(ок))\\s))(\\d+)");
    public static final Pattern SENTENCE_PATTERN = Pattern.compile("(([\\.!?])|(^))((([Рр]ис(\\.\\s?)(\\d+))|[^\\.!?])+[\\.!?])");
    public static final int LINK_GROUP_IN_PATTERN = 8;
    public static final int SENTENCE_GROUP_IN_PATTERN = 4;

    private BufferedReader reader;
    private int highestLinkNumber;
    private boolean isLinksSequential;
    private List<String> sentencesWithLinks;

    public static void main(String[] args) {
        RegExpSearcher searher = null;
        try {
            searher = RegExpSearcher.byFileNameAndEncoding("article.html", Charset.forName("windows-1251"));

            StringBuilder stringBuilder = new StringBuilder("Links in text is ");

            if (searher.isLinksSequential()) {
                stringBuilder.append("sequential. ");
            } else {
                stringBuilder.append("non sequential. ");
            }

            stringBuilder.append("List of all sentences with links (")
                    .append(searher.getSentencesWithLinks().size())
                    .append("):");

            System.out.println(stringBuilder);

            searher.getSentencesWithLinks().stream()
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Something wrong with file or filesystem.");
        }
    }

    private RegExpSearcher() {}

    public static RegExpSearcher from(String stringToSearch) throws IOException {
        RegExpSearcher searher = new RegExpSearcher();
        searher.reader = new BufferedReader(new StringReader(stringToSearch));
        searher.sentencesWithLinks = new ArrayList<>();
        searher.analyseReader();
        return searher;
    }

    public static RegExpSearcher byFileNameAndEncoding(String fileName, Charset charset) throws IOException {
        RegExpSearcher searher = new RegExpSearcher();
        searher.reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));
        searher.sentencesWithLinks = new ArrayList<>();
        searher.analyseReader();
        return searher;
    }

    public List<String> getSentencesWithLinks() {
        return sentencesWithLinks;
    }

    public boolean isLinksSequential() {
        return isLinksSequential;
    }

    private void analyseReader() throws IOException {
        isLinksSequential = true;
        highestLinkNumber = 0;
        String line;

        while ((line = reader.readLine()) != null) {
            Matcher linkMather = LINK_PATTERN.matcher(line);

            int lastSentenceIndex = 0;

            while (linkMather.find()) {
                int linkNumber = Integer.valueOf(linkMather.group(LINK_GROUP_IN_PATTERN));

                if (linkNumber > highestLinkNumber) {
                    highestLinkNumber = linkNumber;
                } else if (linkNumber < highestLinkNumber) {
                    isLinksSequential = false;
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
    }
}

package com.interview.problems.maps;

import java.util.*;

/**
 * Problem 3: Word Frequency Counter
 * 
 * Design a class that counts the frequency of words in a given text and provides various 
 * operations to retrieve statistics about the word frequencies.
 * 
 * Implement methods to:
 * - Count word frequencies
 * - Get the most frequent words
 * - Get the least frequent words
 * - Get frequency of a specific word
 * - Get all words with a specific frequency
 * 
 * Time Complexity: O(n) for counting, O(n log n) for sorting
 * Space Complexity: O(n) where n is the number of unique words
 */
public class WordFrequencyCounter {
    
    private final Map<String, Integer> wordFrequencies;
    
    /**
     * Initialize an empty word frequency counter
     */
    public WordFrequencyCounter() {
        this.wordFrequencies = new HashMap<>();
    }
    
    /**
     * Initialize a word frequency counter with the given text
     * @param text the text to count word frequencies from
     */
    public WordFrequencyCounter(String text) {
        this.wordFrequencies = new HashMap<>();
        addText(text);
    }
    
    /**
     * Process and add text to the word frequency counter
     * Time Complexity: O(n) where n is the number of words
     * @param text the text to process
     */
    public void addText(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }
        
        // Split text into words and remove punctuation
        String[] words = text.toLowerCase()
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .split("\\s+");
        
        // Count word frequencies
        for (String word : words) {
            if (!word.isEmpty()) {
                wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
            }
        }
    }
    
    /**
     * Get the frequency of a specific word
     * Time Complexity: O(1)
     * @param word the word to get frequency for
     * @return the frequency of the word
     */
    public int getWordFrequency(String word) {
        if (word == null) {
            return 0;
        }
        return wordFrequencies.getOrDefault(word.toLowerCase(), 0);
    }
    
    /**
     * Get the total number of unique words
     * Time Complexity: O(1)
     * @return the number of unique words
     */
    public int getUniqueWordCount() {
        return wordFrequencies.size();
    }
    
    /**
     * Get the total number of words
     * Time Complexity: O(n)
     * @return the total number of words
     */
    public int getTotalWordCount() {
        return wordFrequencies.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
    
    /**
     * Get the most frequent words
     * Time Complexity: O(n log k) where k is the number of words to return
     * @param k the number of words to return
     * @return list of the k most frequent words
     */
    public List<Map.Entry<String, Integer>> getMostFrequentWords(int k) {
        if (k <= 0 || wordFrequencies.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Use a priority queue to maintain the top k elements
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );
        
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll(); // Remove the least frequent word
            }
        }
        
        // Convert heap to list in descending order of frequency
        List<Map.Entry<String, Integer>> result = new ArrayList<>(minHeap);
        result.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        return result;
    }
    
    /**
     * Get the least frequent words
     * Time Complexity: O(n log k) where k is the number of words to return
     * @param k the number of words to return
     * @return list of the k least frequent words
     */
    public List<Map.Entry<String, Integer>> getLeastFrequentWords(int k) {
        if (k <= 0 || wordFrequencies.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Use a priority queue to maintain the top k elements (max heap)
        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(
                (a, b) -> b.getValue().compareTo(a.getValue())
        );
        
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            maxHeap.offer(entry);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Remove the most frequent word
            }
        }
        
        // Convert heap to list in ascending order of frequency
        List<Map.Entry<String, Integer>> result = new ArrayList<>(maxHeap);
        result.sort(Comparator.comparingInt(Map.Entry::getValue));
        
        return result;
    }
    
    /**
     * Get all words with a specific frequency
     * Time Complexity: O(n)
     * @param frequency the frequency to look for
     * @return list of words with the specified frequency
     */
    public List<String> getWordsWithFrequency(int frequency) {
        if (frequency <= 0) {
            return new ArrayList<>();
        }
        
        List<String> result = new ArrayList<>();
        
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            if (entry.getValue() == frequency) {
                result.add(entry.getKey());
            }
        }
        
        return result;
    }
    
    /**
     * Get all word frequencies
     * Time Complexity: O(1)
     * @return map of word frequencies
     */
    public Map<String, Integer> getAllWordFrequencies() {
        return new HashMap<>(wordFrequencies);
    }
    
    /**
     * Get all word frequencies sorted by frequency
     * Time Complexity: O(n log n)
     * @param ascending true for ascending order, false for descending
     * @return list of word-frequency entries sorted by frequency
     */
    public List<Map.Entry<String, Integer>> getWordFrequenciesSorted(boolean ascending) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordFrequencies.entrySet());
        
        if (ascending) {
            entries.sort(Comparator.comparingInt(Map.Entry::getValue));
        } else {
            entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        }
        
        return entries;
    }
    
    /**
     * Get all word frequencies sorted alphabetically
     * Time Complexity: O(n log n)
     * @return list of word-frequency entries sorted alphabetically by word
     */
    public List<Map.Entry<String, Integer>> getWordFrequenciesSortedAlphabetically() {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordFrequencies.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getKey));
        return entries;
    }
    
    /**
     * Alternative approach using TreeMap to keep words sorted automatically
     */
    public static class AlphabeticalWordCounter {
        private final TreeMap<String, Integer> wordFrequencies;
        
        public AlphabeticalWordCounter() {
            this.wordFrequencies = new TreeMap<>();
        }
        
        public void addText(String text) {
            if (text == null || text.isEmpty()) {
                return;
            }
            
            String[] words = text.toLowerCase()
                    .replaceAll("[^a-zA-Z0-9\\s]", "")
                    .split("\\s+");
            
            for (String word : words) {
                if (!word.isEmpty()) {
                    wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
                }
            }
        }
        
        public Map<String, Integer> getWordFrequencies() {
            return new TreeMap<>(wordFrequencies);
        }
        
        @Override
        public String toString() {
            return wordFrequencies.toString();
        }
    }
    
    /**
     * Alternative approach that maintains frequencies using frequency buckets
     */
    public static class FrequencyBucketCounter {
        private final Map<String, Integer> wordToFreq;
        private final Map<Integer, Set<String>> freqToWords;
        private int maxFreq;
        
        public FrequencyBucketCounter() {
            this.wordToFreq = new HashMap<>();
            this.freqToWords = new HashMap<>();
            this.maxFreq = 0;
        }
        
        public void addWord(String word) {
            if (word == null || word.isEmpty()) {
                return;
            }
            
            word = word.toLowerCase();
            int oldFreq = wordToFreq.getOrDefault(word, 0);
            int newFreq = oldFreq + 1;
            
            // Update word -> frequency map
            wordToFreq.put(word, newFreq);
            
            // Remove from old frequency bucket
            if (oldFreq > 0) {
                freqToWords.get(oldFreq).remove(word);
                if (freqToWords.get(oldFreq).isEmpty()) {
                    freqToWords.remove(oldFreq);
                }
            }
            
            // Add to new frequency bucket
            freqToWords.computeIfAbsent(newFreq, k -> new HashSet<>()).add(word);
            
            // Update max frequency
            maxFreq = Math.max(maxFreq, newFreq);
        }
        
        public int getWordFrequency(String word) {
            return wordToFreq.getOrDefault(word.toLowerCase(), 0);
        }
        
        public Set<String> getWordsWithFrequency(int frequency) {
            return freqToWords.getOrDefault(frequency, new HashSet<>());
        }
        
        public int getMaxFrequency() {
            return maxFreq;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Word Frequencies:\n");
            for (int freq = maxFreq; freq > 0; freq--) {
                Set<String> words = freqToWords.get(freq);
                if (words != null && !words.isEmpty()) {
                    sb.append("Frequency ").append(freq).append(": ");
                    sb.append(words).append("\n");
                }
            }
            return sb.toString();
        }
    }
    
    public static void main(String[] args) {
        String text = "This is a test. This is only a test. If this had been an actual emergency, "
                + "the Attention Signal you just heard would have been followed by official information, "
                + "news or instructions. This is only a test of the Emergency Broadcast System.";
        
        // Test WordFrequencyCounter
        WordFrequencyCounter counter = new WordFrequencyCounter(text);
        
        System.out.println("Total words: " + counter.getTotalWordCount());
        System.out.println("Unique words: " + counter.getUniqueWordCount());
        System.out.println("Frequency of 'test': " + counter.getWordFrequency("test"));
        
        System.out.println("\nTop 5 most frequent words:");
        List<Map.Entry<String, Integer>> topWords = counter.getMostFrequentWords(5);
        for (Map.Entry<String, Integer> entry : topWords) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println("\nTop 5 least frequent words:");
        List<Map.Entry<String, Integer>> bottomWords = counter.getLeastFrequentWords(5);
        for (Map.Entry<String, Integer> entry : bottomWords) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println("\nWords with frequency 2:");
        List<String> freqWords = counter.getWordsWithFrequency(2);
        System.out.println(freqWords);
        
        System.out.println("\nAll word frequencies (sorted by frequency, descending):");
        List<Map.Entry<String, Integer>> sortedByFreq = counter.getWordFrequenciesSorted(false);
        for (Map.Entry<String, Integer> entry : sortedByFreq) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println("\nAll word frequencies (sorted alphabetically):");
        List<Map.Entry<String, Integer>> sortedAlpha = counter.getWordFrequenciesSortedAlphabetically();
        for (Map.Entry<String, Integer> entry : sortedAlpha) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        // Test AlphabeticalWordCounter
        System.out.println("\n--- AlphabeticalWordCounter ---");
        AlphabeticalWordCounter alphaCounter = new AlphabeticalWordCounter();
        alphaCounter.addText(text);
        System.out.println("Alphabetically sorted words: " + alphaCounter);
        
        // Test FrequencyBucketCounter
        System.out.println("\n--- FrequencyBucketCounter ---");
        FrequencyBucketCounter bucketCounter = new FrequencyBucketCounter();
        for (String word : text.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+")) {
            if (!word.isEmpty()) {
                bucketCounter.addWord(word);
            }
        }
        System.out.println(bucketCounter);
        System.out.println("Max frequency: " + bucketCounter.getMaxFrequency());
        System.out.println("Frequency of 'test': " + bucketCounter.getWordFrequency("test"));
        System.out.println("Words with frequency 3: " + bucketCounter.getWordsWithFrequency(3));
    }
}
package com.interview.problems.regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Problem 3: Log File Parser
 * 
 * Implement a parser for log files that extracts relevant information using regex patterns.
 * The program should be able to parse different log formats and extract fields like:
 * - Timestamp
 * - Log level
 * - Source
 * - Message
 * - IP addresses
 * - User information
 * 
 * Time Complexity: O(n * m) where n is the number of log lines and m is the average line length
 * Space Complexity: O(n) for storing the parsed log entries
 */
public class LogFileParser {
    
    // Log entry model class
    public static class LogEntry {
        private LocalDateTime timestamp;
        private String logLevel;
        private String source;
        private String message;
        private String ipAddress;
        private String userId;
        private Map<String, String> additionalFields;
        
        public LogEntry() {
            this.additionalFields = new HashMap<>();
        }
        
        // Getters and setters
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        
        public String getLogLevel() { return logLevel; }
        public void setLogLevel(String logLevel) { this.logLevel = logLevel; }
        
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        
        public String getIpAddress() { return ipAddress; }
        public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public Map<String, String> getAdditionalFields() { return additionalFields; }
        public void addAdditionalField(String key, String value) { this.additionalFields.put(key, value); }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("LogEntry{");
            if (timestamp != null) sb.append("timestamp=").append(timestamp).append(", ");
            if (logLevel != null) sb.append("logLevel='").append(logLevel).append("', ");
            if (source != null) sb.append("source='").append(source).append("', ");
            if (message != null) sb.append("message='").append(message).append("', ");
            if (ipAddress != null) sb.append("ipAddress='").append(ipAddress).append("', ");
            if (userId != null) sb.append("userId='").append(userId).append("', ");
            sb.append("additionalFields=").append(additionalFields);
            sb.append("}");
            return sb.toString();
        }
    }
    
    // Apache Log Format: 127.0.0.1 - john [10/Oct/2000:13:55:36 -0700] "GET /apache_pb.gif HTTP/1.0" 200 2326
    private static final String APACHE_PATTERN = 
            "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+)\\s?(\\S*)\" (\\d{3}) (\\S+)";
    
    // Common Log Format with Time: 127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] "GET /apache_pb.gif HTTP/1.0" 200 2326
    private static final String COMMON_LOG_PATTERN = 
            "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+)";
    
    // Application log format: 2023-05-15 14:30:45.123 INFO [application] - User login successful. UserId=12345
    private static final String APP_LOG_PATTERN = 
            "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}(?:\\.\\d{3})?)\\s(\\w+)\\s\\[(\\w+)\\]\\s-\\s(.+)$";
    
    // JSON log format: {"timestamp":"2023-05-15T14:30:45.123Z","level":"INFO","logger":"application","message":"User login successful","userId":"12345"}
    private static final String JSON_LOG_PATTERN = 
            "\\{\\s*\"([^\"]+)\"\\s*:\\s*\"([^\"]+)\"(?:\\s*,\\s*\"([^\"]+)\"\\s*:\\s*\"([^\"]+)\")*\\s*\\}";
    
    // IP address pattern
    private static final String IP_PATTERN = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";
    
    // User ID pattern (various formats: user=123, userId=123, "userId":"123")
    private static final String USER_ID_PATTERN = "(?:userId|user)=([\\w-]+)|\"userId\":\"([\\w-]+)\"";
    
    /**
     * Parse Apache/Common log format
     * 
     * @param logLine the log line to parse
     * @return the parsed LogEntry or null if the log line doesn't match
     */
    public static LogEntry parseApacheLog(String logLine) {
        if (logLine == null || logLine.isEmpty()) {
            return null;
        }
        
        Pattern pattern = Pattern.compile(APACHE_PATTERN);
        Matcher matcher = pattern.matcher(logLine);
        
        if (matcher.matches()) {
            LogEntry entry = new LogEntry();
            
            entry.setIpAddress(matcher.group(1));
            entry.setUserId(matcher.group(3).equals("-") ? null : matcher.group(3));
            
            // Parse timestamp
            String timestamp = matcher.group(4);
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");
                entry.setTimestamp(LocalDateTime.parse(timestamp, formatter));
            } catch (Exception e) {
                // Handle timestamp parsing error
                entry.addAdditionalField("rawTimestamp", timestamp);
            }
            
            // Request details
            String method = matcher.group(5);
            String path = matcher.group(6);
            String protocol = matcher.group(7);
            String status = matcher.group(8);
            String bytes = matcher.group(9);
            
            entry.setMessage(method + " " + path + " " + protocol);
            entry.addAdditionalField("status", status);
            entry.addAdditionalField("bytes", bytes);
            entry.addAdditionalField("method", method);
            entry.addAdditionalField("path", path);
            entry.addAdditionalField("protocol", protocol);
            
            return entry;
        }
        
        return null;
    }
    
    /**
     * Parse application log format
     * 
     * @param logLine the log line to parse
     * @return the parsed LogEntry or null if the log line doesn't match
     */
    public static LogEntry parseAppLog(String logLine) {
        if (logLine == null || logLine.isEmpty()) {
            return null;
        }
        
        Pattern pattern = Pattern.compile(APP_LOG_PATTERN);
        Matcher matcher = pattern.matcher(logLine);
        
        if (matcher.matches()) {
            LogEntry entry = new LogEntry();
            
            // Parse timestamp
            String timestamp = matcher.group(1);
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                entry.setTimestamp(LocalDateTime.parse(timestamp, formatter));
            } catch (Exception e) {
                try {
                    // Try without milliseconds
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    entry.setTimestamp(LocalDateTime.parse(timestamp, formatter));
                } catch (Exception ex) {
                    // Handle timestamp parsing error
                    entry.addAdditionalField("rawTimestamp", timestamp);
                }
            }
            
            entry.setLogLevel(matcher.group(2));
            entry.setSource(matcher.group(3));
            entry.setMessage(matcher.group(4));
            
            // Try to extract IP address from the message
            Matcher ipMatcher = Pattern.compile(IP_PATTERN).matcher(entry.getMessage());
            if (ipMatcher.find()) {
                entry.setIpAddress(ipMatcher.group());
            }
            
            // Try to extract user ID from the message
            Matcher userMatcher = Pattern.compile(USER_ID_PATTERN).matcher(entry.getMessage());
            if (userMatcher.find()) {
                entry.setUserId(userMatcher.group(1) != null ? userMatcher.group(1) : userMatcher.group(2));
            }
            
            return entry;
        }
        
        return null;
    }
    
    /**
     * Parse JSON log format
     * 
     * @param logLine the log line to parse
     * @return the parsed LogEntry or null if the log line doesn't match
     */
    public static LogEntry parseJsonLog(String logLine) {
        if (logLine == null || logLine.isEmpty()) {
            return null;
        }
        
        // Note: This is a simplified JSON parser and may not handle all JSON variations
        // For production use, consider using a proper JSON parser
        
        LogEntry entry = new LogEntry();
        
        // Extract key-value pairs
        Pattern keyValuePattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = keyValuePattern.matcher(logLine);
        
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            
            switch (key) {
                case "timestamp":
                    try {
                        // Try ISO format
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                        entry.setTimestamp(LocalDateTime.parse(value, formatter));
                    } catch (Exception e) {
                        entry.addAdditionalField("rawTimestamp", value);
                    }
                    break;
                case "level":
                    entry.setLogLevel(value);
                    break;
                case "logger":
                    entry.setSource(value);
                    break;
                case "message":
                    entry.setMessage(value);
                    break;
                case "userId":
                    entry.setUserId(value);
                    break;
                case "ip":
                case "ipAddress":
                    entry.setIpAddress(value);
                    break;
                default:
                    entry.addAdditionalField(key, value);
                    break;
            }
        }
        
        return entry.getTimestamp() != null || entry.getMessage() != null ? entry : null;
    }
    
    /**
     * Auto-detect and parse log format
     * 
     * @param logLine the log line to parse
     * @return the parsed LogEntry or null if the log line doesn't match any known format
     */
    public static LogEntry parseLog(String logLine) {
        if (logLine == null || logLine.isEmpty()) {
            return null;
        }
        
        // Try each format
        LogEntry entry = parseApacheLog(logLine);
        if (entry != null) return entry;
        
        entry = parseAppLog(logLine);
        if (entry != null) return entry;
        
        entry = parseJsonLog(logLine);
        if (entry != null) return entry;
        
        // No format matched
        return null;
    }
    
    /**
     * Parse multiple log lines
     * 
     * @param logText the log text containing multiple lines
     * @return a list of parsed LogEntry objects
     */
    public static List<LogEntry> parseLogFile(String logText) {
        if (logText == null || logText.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<LogEntry> entries = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new StringReader(logText))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry entry = parseLog(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }
        } catch (IOException e) {
            // Handle IO exception
            System.err.println("Error reading log text: " + e.getMessage());
        }
        
        return entries;
    }
    
    /**
     * Filter log entries by level
     * 
     * @param entries the list of log entries
     * @param level the log level to filter by
     * @return filtered list of log entries
     */
    public static List<LogEntry> filterByLevel(List<LogEntry> entries, String level) {
        if (entries == null || level == null) {
            return new ArrayList<>();
        }
        
        List<LogEntry> filtered = new ArrayList<>();
        for (LogEntry entry : entries) {
            if (level.equalsIgnoreCase(entry.getLogLevel())) {
                filtered.add(entry);
            }
        }
        
        return filtered;
    }
    
    /**
     * Filter log entries by source
     * 
     * @param entries the list of log entries
     * @param source the source to filter by
     * @return filtered list of log entries
     */
    public static List<LogEntry> filterBySource(List<LogEntry> entries, String source) {
        if (entries == null || source == null) {
            return new ArrayList<>();
        }
        
        List<LogEntry> filtered = new ArrayList<>();
        for (LogEntry entry : entries) {
            if (source.equals(entry.getSource())) {
                filtered.add(entry);
            }
        }
        
        return filtered;
    }
    
    /**
     * Filter log entries by message pattern
     * 
     * @param entries the list of log entries
     * @param pattern the regex pattern to match in the message
     * @return filtered list of log entries
     */
    public static List<LogEntry> filterByMessage(List<LogEntry> entries, String pattern) {
        if (entries == null || pattern == null) {
            return new ArrayList<>();
        }
        
        Pattern p = Pattern.compile(pattern);
        List<LogEntry> filtered = new ArrayList<>();
        
        for (LogEntry entry : entries) {
            if (entry.getMessage() != null && p.matcher(entry.getMessage()).find()) {
                filtered.add(entry);
            }
        }
        
        return filtered;
    }
    
    public static void main(String[] args) {
        // Sample log data
        String logs = "127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] \"GET /apache_pb.gif HTTP/1.0\" 200 2326\n" +
                "127.0.0.2 - bob [10/Oct/2000:13:56:36 -0700] \"POST /login.php HTTP/1.0\" 401 1234\n" +
                "2023-05-15 14:30:45.123 INFO [application] - User login successful. UserId=12345 IP=192.168.1.1\n" +
                "2023-05-15 14:31:12.456 ERROR [database] - Connection refused. Retry attempt 1\n" +
                "{\"timestamp\":\"2023-05-15T14:30:45.123Z\",\"level\":\"INFO\",\"logger\":\"auth\",\"message\":\"User authenticated\",\"userId\":\"12345\",\"ipAddress\":\"192.168.1.1\"}\n" +
                "{\"timestamp\":\"2023-05-15T14:35:12.456Z\",\"level\":\"ERROR\",\"logger\":\"database\",\"message\":\"Query timeout\",\"query\":\"SELECT * FROM users\"}";
        
        // Parse log file
        List<LogEntry> entries = parseLogFile(logs);
        
        // Print all entries
        System.out.println("=== ALL LOG ENTRIES ===");
        for (LogEntry entry : entries) {
            System.out.println(entry);
        }
        
        // Filter by log level
        System.out.println("\n=== ERROR LOGS ===");
        List<LogEntry> errorLogs = filterByLevel(entries, "ERROR");
        for (LogEntry entry : errorLogs) {
            System.out.println(entry);
        }
        
        // Filter by source
        System.out.println("\n=== DATABASE LOGS ===");
        List<LogEntry> databaseLogs = filterBySource(entries, "database");
        for (LogEntry entry : databaseLogs) {
            System.out.println(entry);
        }
        
        // Filter by message
        System.out.println("\n=== USER RELATED LOGS ===");
        List<LogEntry> userLogs = filterByMessage(entries, "(?i)user|login|auth");
        for (LogEntry entry : userLogs) {
            System.out.println(entry);
        }
        
        // Demonstrate individual parsers
        System.out.println("\n=== APACHE LOG PARSING ===");
        String apacheLog = "127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] \"GET /apache_pb.gif HTTP/1.0\" 200 2326";
        LogEntry apacheEntry = parseApacheLog(apacheLog);
        System.out.println(apacheEntry);
        
        System.out.println("\n=== APP LOG PARSING ===");
        String appLog = "2023-05-15 14:30:45.123 INFO [application] - User login successful. UserId=12345 IP=192.168.1.1";
        LogEntry appEntry = parseAppLog(appLog);
        System.out.println(appEntry);
        
        System.out.println("\n=== JSON LOG PARSING ===");
        String jsonLog = "{\"timestamp\":\"2023-05-15T14:30:45.123Z\",\"level\":\"INFO\",\"logger\":\"auth\",\"message\":\"User authenticated\",\"userId\":\"12345\",\"ipAddress\":\"192.168.1.1\"}";
        LogEntry jsonEntry = parseJsonLog(jsonLog);
        System.out.println(jsonEntry);
    }
}
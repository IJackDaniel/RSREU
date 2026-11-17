package ru.rsreu.pr5.service;

import java.util.*;


public final class MappingService {
    private final Map<String, Character> mappings = new LinkedHashMap<>();
    private final Deque<Map<String, Character>> history = new ArrayDeque<>();
    private static final int MAX_UNDO = 100;

    public Map<String, Character> view() {
        return Collections.unmodifiableMap(mappings);
    }

    public int size() {
        return mappings.size();
    }

    public void resetHistory() {
        history.clear();
    }

    public void retainCodes(Set<String> codes) {
        mappings.keySet().retainAll(codes);
    }

    public void clear() {
        if (!mappings.isEmpty()) {
            snapshot();
            mappings.clear();
        }
    }

    public void replace(Map<String, Character> next) {
        snapshot();
        mappings.clear();
        mappings.putAll(next);
    }

    // Одна буква может быть назначена только одному коду.
    public void set(String code, Character letter) {
        if (Objects.equals(mappings.get(code), letter)) return;
        snapshot();
        if (letter == null) mappings.remove(code);
        else {
            mappings.entrySet().removeIf(entry -> !entry.getKey().equals(code) && letter.equals(entry.getValue()));
            mappings.put(code, letter);
        }
    }

    public void swap(String first, String second) {
        Character a = mappings.get(first), b = mappings.get(second);
        if (Objects.equals(a, b)) return;
        snapshot();
        if (b == null) mappings.remove(first);
        else mappings.put(first, b);
        if (a == null) mappings.remove(second);
        else mappings.put(second, a);
    }

    public boolean undo() {
        if (history.isEmpty()) return false;
        mappings.clear();
        mappings.putAll(history.pop());
        return true;
    }

    // Сохраняем копию таблицы замен для отмены последнего действия.
    private void snapshot() {
        history.push(new LinkedHashMap<>(mappings));
        while (history.size() > MAX_UNDO) history.removeLast();
    }
}

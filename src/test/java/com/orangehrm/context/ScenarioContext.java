package com.orangehrm.context;

import java.util.HashMap;
import java.util.Map;

/**
 * A per-scenario key/value store. Because Cucumber-PicoContainer creates one
 * instance per scenario and injects it into every step class, this is how data
 * created in one step (e.g. a generated username) is read in a later step
 * without static state - which would break parallel execution.
 */
public class ScenarioContext {

    private final Map<String, Object> store = new HashMap<>();

    public void set(String key, Object value) {
        store.put(key, value);
    }

    public Object get(String key) {
        return store.get(key);
    }

    public String getString(String key) {
        Object v = store.get(key);
        return v == null ? null : v.toString();
    }

    public boolean contains(String key) {
        return store.containsKey(key);
    }
}

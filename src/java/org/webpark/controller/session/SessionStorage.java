/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webpark.controller.session;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import static org.webpark.utils.ProjectUtils.checkNotNull;

/**
 *
 * @author Karichkovskiy Yevhen
 */
public class SessionStorage {

    private final Map<String, HttpSession> sessions;

    private SessionStorage() {
        this.sessions = new HashMap<>();
    }

    public static SessionStorage getInstance() {
        return SessionStorageHolder.INSTANCE;
    }

    public synchronized boolean containsSession(String sessionId) {
        checkNotNull(sessionId);

        return sessions.containsKey(sessionId);
    }

    public synchronized boolean containsSession(HttpSession session) {
        checkNotNull(session);

        return sessions.containsKey(session.getId());
    }

    public synchronized HttpSession getSession(String sessionId) {
        checkNotNull(sessionId);

        return sessions.get(sessionId);
    }

    public synchronized void addSession(HttpSession session) {
        if (session == null) {
            return;
        }

        sessions.put(session.getId(), session);
    }

    private static class SessionStorageHolder {

        private static final SessionStorage INSTANCE = new SessionStorage();
    }
}

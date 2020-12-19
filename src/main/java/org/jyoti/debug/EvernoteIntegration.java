package org.jyoti.debug;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.clients.UserStoreClient;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.thrift.TException;
import com.evernote.thrift.protocol.TProtocol;

import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class EvernoteIntegration {
    private final static String TOKEN = "S=s1:U=9632d:E=17d61b41b11:C=1760a02ee88:P=1cd:A=en-devtoken:V=2:H=57299e2aa84cab1745cc561226d6a47c";
    public static void main(String[] args) throws TException, EDAMSystemException, EDAMUserException {
        EvernoteAuth evernoteAuth = new EvernoteAuth(EvernoteService.SANDBOX, TOKEN);
        ClientFactory factory = new ClientFactory(evernoteAuth);
        UserStoreClient userStore = factory.createUserStoreClient();
        /*boolean versionOk = userStore.checkVersion("Evernote EDAMDemo (Java)",
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MAJOR,
                com.evernote.edam.userstore.Constants.EDAM_VERSION_MINOR);
        if (!versionOk) {
            System.err.println("Incompatible Evernote client protocol version");
            System.exit(1);
        }*/

        // Set up the NoteStore client
        NoteStoreClient noteStore = factory.createNoteStoreClient();
        System.out.println("User : " + userStore.getUser().toString());
        noteStore.listNotebooks()
                .stream()
                .map(notebook -> notebook.getGuid())
                .collect(Collectors.toList())
                .stream().forEach(uid -> {
            try {
                NoteFilter filter = new NoteFilter();
                filter.setNotebookGuid(uid);
                filter.setOrder(NoteSortOrder.CREATED.getValue());
                filter.setAscending(true);
                NoteList notes = noteStore.findNotes(filter, 0, 10);
                notes.getNotes().stream().forEach(note -> {
                    System.out.println("Title: " + note.getTitle());
                    System.out.println("Content : " + note.getContent());
                });
            } catch (EDAMUserException e) {
                e.printStackTrace();
            } catch (EDAMSystemException e) {
                e.printStackTrace();
            } catch (EDAMNotFoundException e) {
                e.printStackTrace();
            } catch (TException e) {
                e.printStackTrace();
            }
        });
    }
}

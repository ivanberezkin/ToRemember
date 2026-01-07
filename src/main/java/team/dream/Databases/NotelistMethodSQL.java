package team.dream.Databases;

import team.dream.shared.Category;
import team.dream.shared.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotelistMethodSQL {

    private static final Connection connToSQL = ConnectionToSQL.getInstance().getConnectionToDb();
    private static final String notelistTableName = "notelist";


    public static void updateNoteInSQL(Note note){
        String sql = String.format("UPDATE notelist SET " +
                "title = ?," +
                "description = ?," +
                "priority = ?," +
                "Category = ?," +
                "isDone = ? " +
                "WHERE noteID = ?;");

        try(PreparedStatement stmt = connToSQL.prepareStatement(sql)){

            stmt.setString(1,note.getTitle());
            stmt.setString(2,note.getDescription());
            stmt.setInt(3,note.getPriorityIndex());
            stmt.setString(4,note.getCategoryEnum().toString());
            stmt.setBoolean(5,note.isDone());
            stmt.setInt(6, note.getNoteID());

            stmt.executeUpdate();

            IO.print("Note " + note.getTitle() + " updated");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void addNewNoteToSQL (Note note){

        String sql = String.format("INSERT INTO %s (memorylistID, title, description, priority, Category, isDone) values " +
                "(?,?,?,?,?,?)",notelistTableName);

        try(PreparedStatement stmt = connToSQL.prepareStatement(sql)){

            stmt.setInt(1,note.getMemoryListID());
            stmt.setString(2,note.getTitle());
            stmt.setString(3,note.getDescription());
            stmt.setInt(4,note.getPriorityIndex());
            stmt.setString(5, note.getCategoryEnum().toString());
            stmt.setBoolean(6, note.isDone());

            stmt.executeUpdate();
            IO.println("Note " + note.getTitle() + " added to noteList ");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

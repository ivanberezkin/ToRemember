package team.dream.Databases;

import team.dream.shared.Category;
import team.dream.shared.MemoryList;
import team.dream.shared.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotelistMethodSQL {

    private static final Connection connToSQL = ConnectionToSQL.getInstance().getConnectionToDb();
    private static final String notelistTableName = "notelist";


    public static void removeNoteInMemoryList(Note note) {

        String sql = "DELETE from notelist where noteID = ?;";

        try (PreparedStatement stmt = connToSQL.prepareStatement(sql)) {

            stmt.setInt(1, note.getNoteID());
            stmt.executeUpdate();

            IO.println("Note succesfully deleted.");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void removeAllNotesInMemoryList(int memoryListID) {

        String sql = "DELETE from notelist where memorylistID = ?;";

        try (PreparedStatement stmt = connToSQL.prepareStatement(sql)) {

            stmt.setInt(1, memoryListID);
            stmt.executeUpdate();

            IO.println("All notes deleted from memorylist");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateNoteInSQL(Note note) {
        String sql = String.format("UPDATE notelist SET " +
                "title = ?," +
                "description = ?," +
                "priority = ?," +
                "Category = ?," +
                "isDone = ? " +
                "WHERE noteID = ?;");

        try (PreparedStatement stmt = connToSQL.prepareStatement(sql)) {

            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getDescription());
            stmt.setInt(3, note.getPriorityIndex());
            stmt.setString(4, note.getCategoryEnum().toString());
            stmt.setBoolean(5, note.isDone());
            stmt.setInt(6, note.getNoteID());

            stmt.executeUpdate();

            IO.print("Note " + note.getTitle() + " updated");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static MemoryList getNotesForMemoryList(MemoryList memoryList){
        String sql = "SELECT * FROM notelist WHERE memoryListID = ?;";

        try(PreparedStatement stmt = connToSQL.prepareStatement(sql)){

            stmt.setInt(1, memoryList.getMemoryListID());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int noteID = rs.getInt(1);
                int memoryListID = rs.getInt(2);
                String title = rs.getString(3);
                String description = rs.getString(4);
                int priority = rs.getInt(5);
                String category = rs.getString(6);
                boolean isDone = rs.getBoolean(7);


                memoryList.addNoteToMemoryList(new Note(noteID,memoryListID,title,description,priority,Category.getCategory(category),isDone));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return memoryList;
    }

    public static void addNewNoteToSQL(Note note) {

        String sql = String.format("INSERT INTO %s (memorylistID, title, description, priority, Category, isDone) values " +
                "(?,?,?,?,?,?)", notelistTableName);

        try (PreparedStatement stmt = connToSQL.prepareStatement(sql)) {

            stmt.setInt(1, note.getMemoryListID());
            stmt.setString(2, note.getTitle());
            stmt.setString(3, note.getDescription());
            stmt.setInt(4, note.getPriorityIndex());
            stmt.setString(5, note.getCategoryEnum().toString());
            stmt.setBoolean(6, note.isDone());

            stmt.executeUpdate();
            IO.println("Note " + note.getTitle() + " added to noteList ");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

package com.example.w21g12_quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.w21g12_quiz.QuizContract.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper  extends SQLiteOpenHelper {

    private SQLiteDatabase MYDATABASE;

    public static final String DBNAME="QuizData.db";

    public DBHelper(@Nullable Context context)

    {
        super(context, DBNAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase MYDATABASE) {
        this.MYDATABASE=MYDATABASE;
      //  MYDATABASE.execSQL("PRAGMA foreign_keys=ON;");

        MYDATABASE.execSQL("drop Table if exists " +QuizContract. QuestionsTable.TABLE_NAME);

        MYDATABASE.execSQL("create Table reviews(review TEXT )"); // table to store reviews
        MYDATABASE.execSQL("create Table users(username TEXT " + //table to store users.
                "primary key,password TEXT)");
        MYDATABASE.execSQL("create Table grades(username TEXT, " + //table to store all classes, grades and total grade.
                "class TEXT, " +
                "grade REAL, " +
                "totalGrade REAL," +
                "PRIMARY KEY (username, class), FOREIGN KEY(username) REFERENCES users(username));");
        MYDATABASE.execSQL("create Table recentGrades(username TEXT, " + //table to store recent grades of quizzes taken.
                "class TEXT, " +
                "grade REAL, " +
                "date TEXT," +
                "PRIMARY KEY (username, class, date), FOREIGN KEY(username) REFERENCES users(username));");
        final String CREATE_QUESTION_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1+ " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_ANSWER_NO + " INTEGER ," +
                QuizContract.QuestionsTable.COLUMN_SUBJECT + " TEXT " +
                ")";

        MYDATABASE.execSQL(CREATE_QUESTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase MYDATABASE, int oldVersion,
                          int newVersion) {
        MYDATABASE.execSQL("drop Table if exists users");
        MYDATABASE.execSQL("drop Table if exists " +QuizContract. QuestionsTable.TABLE_NAME);
        MYDATABASE.execSQL("drop Table if exists grades");
        MYDATABASE.execSQL("drop Table if exists recentGrades");
        MYDATABASE.execSQL("drop Table if exists reviews");
        onCreate(MYDATABASE);


    }



    public void addQuestion(String val1, String val2, String val3, String val4, String val5, int val6, String val7){
        SQLiteDatabase MYDATABASE=this.getWritableDatabase();

        ContentValues val=new ContentValues();
        val.put(QuizContract.QuestionsTable.COLUMN_QUESTION,val1);
        val.put(QuizContract.QuestionsTable.COLUMN_OPTION1,val2);
        val.put(QuizContract.QuestionsTable.COLUMN_OPTION2,val3);
        val.put(QuizContract.QuestionsTable.COLUMN_OPTION3,val4);
        val.put(QuizContract.QuestionsTable.COLUMN_OPTION4,val5);
        val.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NO,val6);
        val.put(QuizContract.QuestionsTable.COLUMN_SUBJECT,val7);


        try{
            long result=MYDATABASE.insert(QuizContract.QuestionsTable.TABLE_NAME,null,val);
            if(result==-1){
                Log.d("QUIZ APP", "Added Question");
            }
            else{
                Log.d("QUIZ APP", "Error adding question.");
            }
        }catch(Exception ex){
            Log.d("QUIZ APP", "Error adding question " + val1);
        }

    }
    public List<Question> browseQuestions(String subject){
        List<Question> questionList=new ArrayList<>();
        MYDATABASE=getReadableDatabase();
        try {
            Cursor cursor = MYDATABASE.rawQuery("SELECT "+QuestionsTable.COLUMN_QUESTION +", " +QuestionsTable.COLUMN_OPTION1 +", "
                    +QuestionsTable.COLUMN_OPTION2 +", " +QuestionsTable.COLUMN_OPTION3 +", " +QuestionsTable.COLUMN_OPTION4 +", "
                    +QuestionsTable.COLUMN_ANSWER_NO + " FROM "
                    + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.COLUMN_SUBJECT +  " = '" + subject + "'", null);
            if (cursor!=null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    Question question=new Question();
                    question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                    question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                    question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                    question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                    question.setOption4(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                    question.setAnswerNo(cursor.getInt(cursor.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NO)));
                    cursor.moveToNext();
                    questionList.add(question);
                }

            }
        }catch(Exception ex){
            Log.d("QUIZ APP","Error in selecting Question table "+ex.getMessage());
        }
        return questionList;
    }
    public Boolean insertData(String username,String password){
        SQLiteDatabase MYDATABASE=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result=MYDATABASE.insert("users",null,contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean insertReview(String review){
        SQLiteDatabase MYDATABASE=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("review",review);
        long result=MYDATABASE.insert("reviews",null,contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public void updateGrade(String username, String className, double grade, double totalGrade){
        MYDATABASE=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("class",className); //java programming, software engineering, python coding, html and css
        contentValues.put("grade",grade);
        contentValues.put("totalGrade",totalGrade);
        try{
            long result=MYDATABASE.insert("grades",null,contentValues);
            if(result==-1){
                Log.d("QUIZ APP", "Added");
            }
            else{
                Log.d("QUIZ APP", "Error adding quiz marks..");
            }
        }catch(Exception ex){
            Log.d("QUIZ APP", "Error adding quiz marks. " + username);
        }
    }




    public Boolean checkusername(String username)
    {
        SQLiteDatabase MYDATABASE=this.getWritableDatabase();
        Cursor cursor=MYDATABASE.rawQuery("Select * from users where username=?" ,
                new String[]{username});
        if(cursor.getCount()>0)

            return true;
        else
            return false;

    }
    public Boolean checkusernamepassword(String username,String password){
        SQLiteDatabase MYDATABASE=this.getWritableDatabase();
        Cursor cursor=MYDATABASE.rawQuery("Select * from users where username=? and password=?" ,
                new String[]{username,password});
        if(cursor.getCount()>0)

            return true;
        else
            return false;
    }

    public List<String[]> browseGrades(String user){
        MYDATABASE = this.getWritableDatabase();
        List<String[]> gradesList = new ArrayList<>();

        String query = ("SELECT * FROM grades WHERE username" +  "= '" + user + "'");
        try{
            Cursor cursor = MYDATABASE.rawQuery(query, null);
            if(cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String[] gradesArray = new String[4];
                    gradesArray[0] = cursor.getString(0); //username
                    gradesArray[1] = cursor.getString(1); //class name
                    gradesArray[2] = cursor.getString(2); //grades
                    gradesArray[3] = cursor.getString(3); //total grades

                    gradesList.add(gradesArray);
                    cursor.moveToNext();
                }
            }

        }catch (Exception ex){
            Log.d("QUIZ APP", "Error querying grades data");
        }

        return gradesList;
    }
    public double gradePercent(String user, String className){ //this method takes the cumulative mark and changes it to a percent to work better with the graphs.
        MYDATABASE = this.getWritableDatabase();
        String query = ("SELECT * FROM grades WHERE username" +  "= '" + user + "' AND class" +  "= '" + className + "' ");
        double percent = 0;
        try{
            Cursor cursor = MYDATABASE.rawQuery(query, null);
            if(cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    double oldGrade = cursor.getDouble(2);
                    double totalGrade = cursor.getDouble(3);
                    percent = (oldGrade/totalGrade) * 100;
                    cursor.moveToNext();
                }
            }

        }catch (Exception ex){
            Log.d("QUIZ APP", "Error changing grade into percent.");
        }
        return percent;
    }

    public void updateNewGrade(String user, String className, double totalOutOf, double score){ //this method takes the score and total of the quiz the user takes, and updates the database with a cumulative mark.
        MYDATABASE = this.getWritableDatabase(); //this method will be used when the quiz page is done, and will be used to give a mark to the student.
        String query = ("SELECT * FROM grades WHERE username" +  "= '" + user + "' AND class" +  "= '" + className + "' ");
        double percent = 0;
        try{
            Cursor cursor = MYDATABASE.rawQuery(query, null);
            if(cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    ContentValues contentValues = new ContentValues();
                    double oldGrade = cursor.getDouble(2);
                    double totalGrade = cursor.getDouble(3);
                    double newGrade = oldGrade + score;
                    double newTotal = totalGrade + totalOutOf;
                    contentValues.put("grade", newGrade);
                    contentValues.put("totalGrade", newTotal);
                    MYDATABASE.update("grades", contentValues, "username" +  "= '" + user + "' AND class" +  "= '" + className + "' ",null);
                    cursor.moveToNext();
                }
            }

        }catch (Exception ex){
            Log.d("QUIZ APP", "Error changing grade into percent.");
        }
    }

    public void addRecentQuiz(String user, String className, double grade, String date){
        MYDATABASE = this.getWritableDatabase();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = formatter.format(new Date());
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",user);
        contentValues.put("class",className);
        contentValues.put("grade",grade);
        contentValues.put("date", date);
        try{
            long result=MYDATABASE.insert("recentGrades",null,contentValues);
            if(result==-1){
                Log.d("QUIZ APP", "Added");
            }
            else{
                Log.d("QUIZ APP", "Error adding recent quiz.");
            }
        }catch(Exception ex){
            Log.d("QUIZ APP", "Error adding recent quiz. " + ex);
        }
    }

    public List<String[]> browseRecentGrades(String user){
        MYDATABASE = this.getWritableDatabase();
        List<String[]> gradesList = new ArrayList<>();

        String query = ("SELECT * FROM recentGrades WHERE username" +  "= '" + user + "'");
        try{
            Cursor cursor = MYDATABASE.rawQuery(query, null);
            if(cursor != null){
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    String[] gradesArray = new String[3];
                    gradesArray[0] = cursor.getString(1); //class name
                    gradesArray[1] = cursor.getString(2); //grade
                    gradesArray[2] = cursor.getString(3); //date

                    gradesList.add(gradesArray);
                    cursor.moveToNext();
                }
            }

        }catch (Exception ex){
            Log.d("QUIZ APP", "Error querying grades data");
        }

        return gradesList;
    }

//    public List<String> browseReviews(){
//        MYDATABASE = this.getWritableDatabase();
//        List<String> reviews = new ArrayList<>();
//
//        String query = ("SELECT * FROM reviews");
//        try{
//            Cursor cursor = MYDATABASE.rawQuery(query, null);
//            if(cursor != null){
//                cursor.moveToFirst();
//                while(!cursor.isAfterLast()){
//                    String reviewArray;
//                    reviewArray = cursor.getString(0);
//                    reviews.add(reviewArray);
//                    cursor.moveToNext();
//                }
//            }
//
//        }catch (Exception ex){
//            Log.d("QUIZ APP", "Error querying review data");
//        }
//
//        return reviews;
//    }


    public boolean dataExist() {
        MYDATABASE=getReadableDatabase();

            Cursor cursor = MYDATABASE.rawQuery("SELECT * FROM "
                    + QuestionsTable.TABLE_NAME, null);
            if(cursor.getCount()<=0){
                Log.d("QUIZ APP", "Cursor value " + cursor);
                return true;
            }
            else
                return false;

    }

}

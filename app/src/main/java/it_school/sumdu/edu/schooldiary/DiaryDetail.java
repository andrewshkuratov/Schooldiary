package it_school.sumdu.edu.schooldiary;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;

public class DiaryDetail extends AppCompatActivity {

    ListView listView;
    CustomAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework_list);
        listView = (ListView)findViewById(R.id.listView);
        ArrayList<String> subjectlist = getIntent().getStringArrayListExtra("subjectlist");
        ArrayList<String> tasklist = getIntent().getStringArrayListExtra("tasklist");

        // Использование собственного шаблона
        adapter = new CustomAdapter(getApplicationContext(), R.layout.homework_list_row);

        listView.setAdapter(adapter);
        int i = 0;
        for(String subjects: subjectlist){
            DiaryProvider diaryProvider = new DiaryProvider(subjects, tasklist.get(i));
            adapter.add(diaryProvider); i++;
        }
    }
}
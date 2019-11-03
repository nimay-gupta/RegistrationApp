package com.androidtuts4u.arun.registartionapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.db.AppDAO;


@Database(entities = {Entity.class}, version = 1, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {

    private static MyAppDatabase INSTANCE;

    public abstract AppDAO dao();

    public static MyAppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), MyAppDatabase.class, "player-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();

            INSTANCE.dao().purge();
            Entity elem;
            elem = new Entity("Zen","If you chase two rabbits, you catch none.","",-1);
            INSTANCE.dao().insertAll(elem);
            int id_zen = INSTANCE.dao().findByName("Zen").get(0).getId();
            elem = new Entity("Acads","Padhai ki baatein","31/12/2019",id_zen);
            INSTANCE.dao().insertAll(elem);
            elem = new Entity("Hobbies","<3","",id_zen);
            INSTANCE.dao().insertAll(elem);
            int id_hobbs = INSTANCE.dao().findByName("Hobbies").get(0).getId();
            elem = new Entity("Self_improvement","Reading list, blogs, exercise, etc.","30/12/2019",id_zen);
            INSTANCE.dao().insertAll(elem);
            int id_selfimp = INSTANCE.dao().findByName("Self_improvement").get(0).getId();
            elem = new Entity("Research","Pet projects","",id_zen);
            INSTANCE.dao().insertAll(elem);
            elem = new Entity("Origami","cranes and tigers.","29/10/2019",id_hobbs);
            INSTANCE.dao().insertAll(elem);
            elem = new Entity("Drum_practice","Aim:\nHallowed be thy name,\nAcid Rain (LTE)","29/10/2019",id_hobbs);
            INSTANCE.dao().insertAll(elem);
            elem = new Entity("Exercise","someday?","29/2/2019",id_selfimp);
            INSTANCE.dao().insertAll(elem);
            elem = new Entity("Reading_list","My bucket list:\nHear the Wind Sing\nThe Fountainhead\nAtlas Shrugged\nA prisoner of birth","",id_selfimp);
            INSTANCE.dao().insertAll(elem);
            
            
            
            
        }
        return INSTANCE;
    }

    public static String hier(int id) {
        Entity ent = INSTANCE.dao().findById(id);
        if(ent.getName().equals("Zen")) {
            return "Zen";
        } else {
            return hier(ent.getParent_id()) + "/" + ent.getName();
        }
    }

}

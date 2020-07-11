package macario.tala.covidtracker

import android.content.Context
import androidx.room.*
import java.util.*

//Entities
@Entity
data class Userid(@PrimaryKey val entityId:String)

@Entity
data class Contact(@PrimaryKey val contactId: Int, var contactName: String, var dateOfContact: String )

@Entity
data class ContactMap(@PrimaryKey var contactName:String, var entityId: String )

//DAO
val firstUUID = Userid(entityId = UUID.randomUUID().toString())
@Dao
interface iUserDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createUniqueUser(userid: Userid)
    @Query("Select entityId from Userid Limit 1")
    fun getEntityId():String
}
@Dao
interface iContactDao {
    @Insert
    fun recordContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("Select * FROM Contact Order by DATE(Contact.DateOfContact) Desc")
    fun loadContacts():Array<Contact>

    @Query("Select * from Contact WHERE ContactName in (:in_ContactName) Order by DATE(Contact.DateOfContact) Desc")
    fun getContactsWithPerson(in_ContactName: String ):Array<Contact>
}
@Dao
interface iContactMapDao{
    @Insert
    fun createContactMap(contactMap: ContactMap)

    @Update
    fun updateContactMap(contactMap: ContactMap)
}

//Database
@Database(entities = arrayOf(Userid::class,Contact::class,ContactMap::class),version = 1)
abstract class CovidDB: RoomDatabase(){
    abstract fun useridDao(): iUserDao
    abstract fun contactDao() : iContactDao
    abstract fun contactMapDao(): iContactMapDao

    companion object{
        private var CovidDatabaseSingleton: CovidDB?=null

        fun getDatabase(context:Context):CovidDB?{
            if (CovidDatabaseSingleton==null){
                synchronized(CovidDB::class.java)
                {
                    if(CovidDatabaseSingleton==null)
                    {
                        CovidDatabaseSingleton=Room.databaseBuilder<CovidDB>(context.applicationContext,
                        CovidDB::class.java,
                        "CovidTrackerDB")
                            .build()
                    }
                }
            }
            return CovidDatabaseSingleton
        }
    }
}

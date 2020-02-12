import com.learn.lockopensystem.model.getresult.*
import com.learn.lockopensystem.model.postbody.*
import io.reactivex.Observable
import retrofit2.http.*

public interface Api {
    @FormUrlEncoded
    @POST("/sendcode.asp")
    fun SMSAuth(@Field("phonenum") phonenum: String): Observable<Result>

    @FormUrlEncoded
    @POST("/register.asp")
    fun register(@FieldMap params: Map<String, String>): Observable<Result>

    @FormUrlEncoded
    @POST("/resetpass.asp")
    fun resetPassword(@FieldMap params: Map<String, String>): Observable<Result>

    @FormUrlEncoded
    @POST("/login.asp")
    fun login(@FieldMap params: Map<String, String>): Observable<OauthResult>

    @FormUrlEncoded
    @POST("/getuserinfo.asp")
    fun getUserInfo(@Field("token") token: String): Observable<UserInfo>

    @FormUrlEncoded
    @POST("/logout.asp")
    fun logout(@Field("token") token: String): Observable<Result>

    @FormUrlEncoded
    @POST("/getuserlock.asp")
    fun getuserlock(@Field("token") token: String): Observable<LockInfo>

    @FormUrlEncoded
    @POST("/dellock.asp")
    fun delLock(@Field("token") token: String, @Field("lockid") lockid: String): Observable<Result>

    @FormUrlEncoded
    @POST("/changelockname.asp")
    fun changeLockName(@Field("token") token: String,
                       @Field("lockid") lockid: String,
                       @Field("lockname") lockname: String): Observable<Result>

    @FormUrlEncoded
    @POST("/lockopen.asp")
    fun lockOpen(@Field("token") token: String, @Field("lockid") lockid: String): Observable<Result>

    @FormUrlEncoded
    @POST("/getlockopenrecord.asp")
    fun getLockOpenRecord(@Field("token") token: String, @Field("lockid") lockid: String): Observable<UnLockHistory>

    @FormUrlEncoded
    @POST("/getlockalarm.asp")
    fun getLockAlarm(@Field("token") token: String, @Field("lockid") lockid: String): Observable<WarningInfo>

    @FormUrlEncoded
    @POST("/getnewalarm.asp")
    fun getNewAlarm(@Field("token") token: String, @Field("lockid") lockid: String): Observable<NewWarningInfo>

}
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
    fun register(@Field("phonenum") phonenum: String,
                 @Field("smscode") smscode: String,
                 @Field("password") password: String,
                 @Field("nickname") nickname: String): Observable<Result>

    @FormUrlEncoded
    @POST("/resetpass.asp")
    fun resetPassword(@Field("phonenum") phonenum: String,
                      @Field("smscode") smscode: String,
                      @Field("password") password: String): Observable<Result>

    @FormUrlEncoded
    @POST("/login.asp")
    fun login(@Field("phonenum") phonenum: String, @Field("password") password: String): Observable<OauthResult>

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
    fun changeLockName(
        @Field("token") token: String,
        @Field("lockid") lockid: String,
        @Field("lockname") lockname: String
    ): Observable<Result>

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
    fun getNewAlarm(@Field("token") token: String): Observable<NewWarningInfo>

    @FormUrlEncoded
    @POST("/addlock.asp")
    fun addLock(@Field("lockid") lockid: String, @Field("lockpass") lockpass: String, @Field("token") token: String): Observable<Result>

}
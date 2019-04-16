package com.example.param.recruit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface Api {

    @FormUrlEncoded
    @POST("login_employee.php")
    Call<JsonObject> loginEmployee(
      @Field("username") String username,
      @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login_employer.php")
    Call<JsonObject> loginEmployer(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register_employee.php")
    Call<JsonObject> registerEmployee(
            @Field("username") String username,
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("dob") String dob
    );

    @FormUrlEncoded
    @POST("register_employer.php")
    Call<JsonObject> registerEmployer(
            @Field("username") String username,
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("dob") String dob,
            @Field("org") String org
    );

    @FormUrlEncoded
    @POST("employee_details.php")
    Call<JsonObject> employeeDetails(
            @Field("employee_id") int employeeId
    );

    @FormUrlEncoded
    @POST("employer_details.php")
    Call<JsonObject> employerDetails(
            @Field("employer_id") int employerId
    );

    @FormUrlEncoded
    @POST("job_details.php")
    Call<JsonObject> jobDetails(
            @Field("job_id") int jobId
    );

    @FormUrlEncoded
    @POST("job_skills.php")
    Call<JsonObject> jobSkills(
            @Field("job_id") int jobId
    );

    @FormUrlEncoded
    @POST("find_jobs.php")
    Call<JsonObject> findJobs(
            @Field("employee_id") int employee_id
    );

    @FormUrlEncoded
    @POST("fetch_applications.php")
    Call<JsonObject> fetchApplications(
            @Field("employee_id") int employee_id
    );

    @FormUrlEncoded
    @POST("fetch_applications_employer.php")
    Call<JsonObject> fetchApplicationsEmployer(
            @Field("employer_id") int employer_id
    );

    @FormUrlEncoded
    @POST("application_details.php")
    Call<JsonObject> appliciationDetails(
            @Field("employee_id") int employee_id,
            @Field("job_id") int job_id
    );

    @FormUrlEncoded
    @POST("org_name.php")
    Call<JsonObject> orgName(
            @Field("employer_id") int employer_id
    );

    @FormUrlEncoded
    @POST("post_job.php")
    Call<JsonObject> postJob(
            @Field("job_type") String job_type,
            @Field("description") String description,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("salary") int salary,
            @Field("location") String location,
            @Field("employer_id") int employer_id
    );

    @FormUrlEncoded
    @POST("add_job_skill.php")
    Call<JsonObject> addJobSkill(
            @Field("job_id") int job_id,
            @Field("skill") String skill,
            @Field("duration") int duration
    );

    @FormUrlEncoded
    @POST("accept_application.php")
    Call<JsonObject> acceptApplication(
            @Field("job_id") int job_id,
            @Field("employee_id") int employee_id
    );

    @FormUrlEncoded
    @POST("reject_application.php")
    Call<JsonObject> rejectApplication(
            @Field("job_id") int job_id,
            @Field("employee_id") int employee_id
    );

    @FormUrlEncoded
    @POST("apply_application.php")
    Call<JsonObject> applyApplication(
            @Field("job_id") int job_id,
            @Field("employee_id") int employee_id,
            @Field("sop") String sop
    );

    @FormUrlEncoded
    @POST("cancel_application.php")
    Call<JsonObject> cancelApplication(
            @Field("job_id") int job_id,
            @Field("employee_id") int employee_id
    );
}

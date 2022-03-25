package com.ldhcjs.androidprivatemessenger.fcm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReceivedMsg {
    @SerializedName("multicast_id")
    @Expose
    private var multicastId: Long? = null

    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("failure")
    @Expose
    private var failure: Int? = null

    @SerializedName("canonical_ids")
    @Expose
    private var canonicalIds: Int? = null

    @SerializedName("results")
    @Expose
    private var results: List<Result?>? = null

    fun getMulticastId(): Long? {
        return multicastId
    }

    fun setMulticastId(multicastId: Long?) {
        this.multicastId = multicastId
    }

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getFailure(): Int? {
        return failure
    }

    fun setFailure(failure: Int?) {
        this.failure = failure
    }

    fun getCanonicalIds(): Int? {
        return canonicalIds
    }

    fun setCanonicalIds(canonicalIds: Int?) {
        this.canonicalIds = canonicalIds
    }

    fun getResults(): List<Result?>? {
        return results
    }

    fun setResults(results: List<Result?>?) {
        this.results = results
    }
}
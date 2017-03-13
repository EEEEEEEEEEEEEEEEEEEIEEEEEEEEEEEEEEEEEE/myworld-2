package com.artist.myworld.rpc;

import com.dili.utils.boot.retrofitful.annotation.POST;
import com.dili.utils.boot.retrofitful.annotation.Restful;
import com.dili.utils.boot.retrofitful.annotation.VOBody;
import com.dili.utils.domain.BaseOutput;

/**
 * Created by asiam on 2017/3/10 0010.
 */
@Restful("http://localhost")
public interface UserRpc {
    @POST("/api/forgot/updateSellerPasswordByPhoneNumber")
    public BaseOutput updateSellerPasswordByPhoneNumber(@VOBody String str);

}

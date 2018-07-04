/*
 * DataManager.java
 *
 * Created by quan.p@homecredit.vn
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 6/12/18 4:46 PM
 */

package vn.homecredit.hcvn.data;

import vn.homecredit.hcvn.data.local.memory.MemoryHelper;
import vn.homecredit.hcvn.data.remote.RestService;
import vn.homecredit.hcvn.data.remote.acl.AclRestService;

public interface DataManager extends RestService, MemoryHelper, AclRestService {

}

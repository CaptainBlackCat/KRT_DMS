package com.krt.home.providers.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.krt.business.bean.CustomerLevelListEntity;
import com.krt.home.R;

public class SubmarinesCenterAdapter extends BaseQuickAdapter<CustomerLevelListEntity, BaseViewHolder> {

    public SubmarinesCenterAdapter() {
        super(R.layout.home_item_submarines_view, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerLevelListEntity item) {
        helper.setText(R.id.leads_level, item.getDictValue().substring(0, 1));
        helper.setText(R.id.count, item.getCueCount() + "人");
    }
}

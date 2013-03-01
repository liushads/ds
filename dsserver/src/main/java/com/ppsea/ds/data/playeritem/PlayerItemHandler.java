package com.ppsea.ds.data.playeritem;

import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.PlayerItem;

public interface PlayerItemHandler {
	public ErrorMsg use(PlayerItem playerItem);
	public ErrorMsg unuse(PlayerItem playerItem);
}

<#-- 显示可以合成的物品列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
分解大师:你好! 有什么可以为你帮忙的吗?<br/>
（请锁定您行囊中不想分解的贵重物品再进行分解）<br/>
<@a href="/p?n_itf/${pid}/d/r/"/>装备分解介绍</a><br/>
<@a href="/p?n_itf/${pid}/d/i/10437/"/>分解灭风•真</a><br/>
<@a href="/p?n_itf/${pid}/d/i/10438/"/>分解降龙•真</a><br/>
<@a href="/p?n_itf/${pid}/d/i/10439/"/>分解归虚•真</a><br/>
<@a href="/p?n_itf/${pid}/d/i/10440/"/>分解长歌•真</a><br/>
<@a href="/p?n_itf/${pid}/d/i/10441/"/>分解鬼泣•真</a><br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p>
</card>
</wml>
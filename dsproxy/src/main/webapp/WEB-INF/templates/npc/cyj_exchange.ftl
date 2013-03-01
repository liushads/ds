<#include "/include/header.ftl">
<card title="${game_title}"><p>
月牙村校场<br/>
扫墓的百姓:任务多余的材料每10个我还可以给你换1银合成的物品不提供兑换。<br/>
<#if totalAmount &gt; 0>
<#if totalAmount &gt; 9>
你的剩余材料总数为${totalAmount}件(${note_info}),可以兑换到${money}X1银<br/>
<#else>	
你的剩余材料总数为${totalAmount}件(${note_info}),不足以兑换<br/>
</#if>
<#else>
你没有剩余材料<br/>
</#if>
<@a href="/p?n_cyj/${pid}/e/1/"/>全部兑换</a><br/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>
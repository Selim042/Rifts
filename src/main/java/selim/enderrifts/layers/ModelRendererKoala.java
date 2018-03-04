package selim.enderrifts.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelRendererKoala extends ModelRenderer {

	public ModelRendererKoala(ModelBase model, String boxNameIn) {
		super(model, boxNameIn);
	}

	public ModelRendererKoala(ModelBase model) {
		this(model, (String) null);
	}

	public ModelRendererKoala(ModelBase model, int texOffX, int texOffY) {
		this(model);
		this.setTextureOffset(texOffX, texOffY);
	}

	@Override
	public void render(float scale) {
		super.render(scale);
	}

}

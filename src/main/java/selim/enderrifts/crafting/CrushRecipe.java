package selim.enderrifts.crafting;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import selim.enderrifts.misc.IJsonParser;

public abstract class CrushRecipe extends IForgeRegistryEntry.Impl<CrushRecipe> {

	private final ItemStack output;

	public CrushRecipe(ItemStack output) {
		this.output = output;
	}

	public abstract boolean isInputValid(ItemStack input);

	public abstract int getConsumed(ItemStack input);

	public abstract int getCrafted(ItemStack input);

	public abstract List<ItemStack> getPossibleInputs();

	public ItemStack getResult() {
		return this.output;
	}

	public static IJsonParser<CrushRecipe> getParser() {
		return new IJsonParser<CrushRecipe>() {

			@Override
			public CrushRecipe parse(JsonObject json, JsonContext ctx) {
				Ingredient input = CraftingHelper.getIngredient(json.get("input"), ctx);
				ItemStack output = CraftingHelper.getItemStack(json.get("output").getAsJsonObject(),
						ctx);
				return new IngredientCrushRecipe(output, input);
			}

			@Override
			public Class<CrushRecipe> getType() {
				return CrushRecipe.class;
			}

		};
	}

}

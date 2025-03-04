package hu.bme.mit.ftsrg.hypernate.samples.assettransfer;

import hu.bme.mit.ftsrg.hypernate.context.HypernateContext;
import hu.bme.mit.ftsrg.hypernate.contract.HypernateContract;
import hu.bme.mit.ftsrg.hypernate.entity.EntityExistsException;
import hu.bme.mit.ftsrg.hypernate.entity.EntityNotFoundException;
import hu.bme.mit.ftsrg.hypernate.registry.Registry;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeException;

import java.util.List;

import static org.hyperledger.fabric.contract.annotation.Transaction.TYPE.SUBMIT;
import static org.hyperledger.fabric.contract.annotation.Transaction.TYPE.EVALUATE;

@Contract(
    name = "basic",
    info =
        @Info(
            title = "Asset Transfer with Hypernate",
            description = "The hyperlegendary asset transfer reimplemeented with Hypernate",
            version = "0.1.0",
            license =
                @License(
                    name = "Apache 2.0 License",
                    url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
            contact =
                @Contact(
                    email = "a.transfer@example.com",
                    name = "Adrian Transfer",
                    url = "https://hyperledger.example.com")))
@Default
public class AssetTransfer implements HypernateContract {

  @Transaction(intent = EVALUATE)
  public boolean AssetExists(final HypernateContext ctx, final String assetID) {
    try {
      ctx.getRegistry().mustRead(Asset.class, assetID);
      return true;
    } catch (final EntityNotFoundException _e) {
      return false;
    }
  }

  @Transaction(intent = SUBMIT)
  public void CreateAsset(
      final HypernateContext ctx,
      final String assetID,
      final String color,
      final int size,
      final String owner,
      final int appraisedValue) {
    try {
      ctx.getRegistry()
          .mustCreate(
              Asset.builder()
                  .assetID(assetID)
                  .color(color)
                  .size(size)
                  .owner(owner)
                  .appraisedValue(appraisedValue)
                  .build());
    } catch (final EntityExistsException _e) {
      final String errorMessage = String.format("Asset %s already exists", assetID);
      System.out.printf(errorMessage);
      throw new ChaincodeException(
          errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
    }
  }

  @Transaction(intent = SUBMIT)
  public void DeleteAsset(final HypernateContext ctx, final String assetID) {
    final Registry reg = ctx.getRegistry();
    try {
      Asset toDelete = reg.mustRead(Asset.class, assetID);
      ctx.getRegistry().mustDelete(toDelete);
    } catch (final EntityNotFoundException _e) {
      final String errorMessage = String.format("Asset %s does not exist", assetID);
      System.out.printf(errorMessage);
      throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
    }
  }

  @Transaction(intent = EVALUATE)
  public List<Asset> GetAllAssets(final HypernateContext ctx) {
    return ctx.getRegistry().readAll(Asset.class);
  }

  @Transaction(intent = SUBMIT)
  public void InitLedger(final HypernateContext ctx) {
    /* Try to create some Assets via the registry -- ignore if they already exist */
    final Registry reg = ctx.getRegistry();
    reg.tryCreate(
        Asset.builder()
            .assetID("asset1")
            .color("blue")
            .size(5)
            .owner("Tomoko")
            .appraisedValue(300)
            .build());
    reg.tryCreate(
        Asset.builder()
            .assetID("asset2")
            .color("red")
            .size(5)
            .owner("Brad")
            .appraisedValue(400)
            .build());
    reg.tryCreate(
        Asset.builder()
            .assetID("asset3")
            .color("green")
            .size(10)
            .owner("Jin Soo")
            .appraisedValue(500)
            .build());
    reg.tryCreate(
        Asset.builder()
            .assetID("asset4")
            .color("yellow")
            .size(10)
            .owner("Max")
            .appraisedValue(600)
            .build());
    reg.tryCreate(
        Asset.builder()
            .assetID("asset5")
            .color("black")
            .size(15)
            .owner("Adrian")
            .appraisedValue(700)
            .build());
    reg.tryCreate(
        Asset.builder()
            .assetID("asset6")
            .color("white")
            .size(15)
            .owner("Michel")
            .appraisedValue(700)
            .build());
  }

  @Transaction(intent = EVALUATE)
  public Asset ReadAsset(final HypernateContext ctx, final String assetID) {
    try {
      return ctx.getRegistry().mustRead(Asset.class, assetID);
    } catch (final EntityNotFoundException _e) {
      final String errorMessage = String.format("Asset %s does not exist", assetID);
      System.out.printf(errorMessage);
      throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
    }
  }

  @Transaction(intent = SUBMIT)
  public String TransferAsset(
      final HypernateContext ctx, final String assetID, final String newOwner) {
    final Registry reg = ctx.getRegistry();
    final Asset asset;
    try {
      asset = reg.mustRead(Asset.class, assetID);
    } catch (final EntityNotFoundException _e) {
      final String errorMessage = String.format("Asset %s does not exist", assetID);
      System.out.printf(errorMessage);
      throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
    }

    asset.setOwner(newOwner);

    reg.mustUpdate(asset);
    return asset.getOwner();
  }

  @Transaction(intent = SUBMIT)
  public Asset UpdateAsset(
      final HypernateContext ctx,
      final String assetID,
      final String color,
      final int size,
      final String owner,
      final int appraisedValue) {
    Asset updatedAsset =
        Asset.builder()
            .assetID(assetID)
            .color(color)
            .size(size)
            .owner(owner)
            .appraisedValue(appraisedValue)
            .build();
    try {
      ctx.getRegistry().mustUpdate(updatedAsset);
      return updatedAsset;
    } catch (final EntityNotFoundException _e) {
      final String errorMessage = String.format("Asset %s does not exist", assetID);
      System.out.printf(errorMessage);
      throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
    }
  }

  private enum AssetTransferErrors {
    ASSET_NOT_FOUND,
    ASSET_ALREADY_EXISTS
  }
}

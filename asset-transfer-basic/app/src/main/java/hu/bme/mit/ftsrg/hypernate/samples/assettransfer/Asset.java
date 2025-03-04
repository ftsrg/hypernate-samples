package hu.bme.mit.ftsrg.hypernate.samples.assettransfer;

import hu.bme.mit.ftsrg.hypernate.annotations.AttributeInfo;
import hu.bme.mit.ftsrg.hypernate.annotations.PrimaryKey;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.NonFinal;

@Data
@Builder
@FieldNameConstants
@PrimaryKey(@AttributeInfo(name = Asset.Fields.assetID))
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class Asset {

  String assetID;
  String color;
  int size;
  int appraisedValue;
  @NonFinal String owner;
}

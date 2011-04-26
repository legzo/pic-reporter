package org.legzo.picreporter.model;

import java.io.File;
import java.io.IOException;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.RationalNumber;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Image {

	private final static Logger logger = LoggerFactory.getLogger(Image.class);

	private File file;
	private FocalLength focalLength;
	private Aperture aperture;
	private ExposureTime exposureTime;

	public Image(String fileName) {
		this.setFile(new File(fileName));
	}

	public Image(File file) {
		this.setFile(file);
	}

	public void loadExif() {
		try {
			IImageMetadata metadata = Sanselan.getMetadata(file);

			this.setAperture((Aperture) getFieldValue(metadata, TiffConstants.EXIF_TAG_FNUMBER));
			this.setExposureTime((ExposureTime) getFieldValue(metadata, TiffConstants.EXIF_TAG_EXPOSURE_TIME));
			this.setFocalLength((FocalLength) getFieldValue(metadata, TiffConstants.EXIF_TAG_FOCAL_LENGTH));

		} catch (ImageReadException e) {
			logger.error("ImageReadException occured reading metadata for file {}", file.getPath());
			logger.error("Exception :", e);
		} catch (IOException e) {
			logger.error("IOException occured reading metadata for file {}", file.getPath());
			logger.error("Exception :", e);
		}

	}

	private Parameter getFieldValue(IImageMetadata meta, TagInfo field) {
		Parameter result = null;
		TiffField fieldValue = null;
		if (meta instanceof JpegImageMetadata) {
			JpegImageMetadata jpgMeta = (JpegImageMetadata) meta;
			fieldValue = jpgMeta.findEXIFValue(field);
		}
		if (meta instanceof TiffImageMetadata) {
			TiffImageMetadata tiffMeta = (TiffImageMetadata) meta;
			try {
				fieldValue = tiffMeta.findField(field);
			} catch (ImageReadException e) {
				e.printStackTrace();
			}
		}

		if (fieldValue != null) {
			Object value;
			try {
				value = fieldValue.getValue();
				if (value instanceof RationalNumber) {
					RationalNumber rational = (RationalNumber) value;

					if (field == TiffConstants.EXIF_TAG_FOCAL_LENGTH) {
						result = new FocalLength(rational.floatValue());
					} else if (field == TiffConstants.EXIF_TAG_FNUMBER) {
						result = new Aperture(rational.floatValue());
					} else if (field == TiffConstants.EXIF_TAG_EXPOSURE_TIME) {
						result = new ExposureTime(rational.numerator == 10 ? rational.divisor / 10 : rational.divisor);
					}
				}
			} catch (ImageReadException e) {
				logger.error("ImageReadException trying to get value of field {} for image {}", field, this.file
						.getPath());
				logger.error("Exception :", e);
			}

		}
		return result;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFocalLength(FocalLength focalLength) {
		this.focalLength = focalLength;
	}

	public FocalLength getFocalLength() {
		return focalLength;
	}

	public void setAperture(Aperture aperture) {
		this.aperture = aperture;
	}

	public Aperture getAperture() {
		return aperture;
	}

	public void setExposureTime(ExposureTime exposureTime) {
		this.exposureTime = exposureTime;
	}

	public ExposureTime getExposureTime() {
		return exposureTime;
	}

}
